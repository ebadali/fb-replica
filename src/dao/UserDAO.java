package dao;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import container.Data;
import entity.CFile;
import entity.Person;
import entity.Post;

public class UserDAO {
	Connection currentCon = null;
	ResultSet rs = null;
	Statement stmt = null;

	List<Post> listOfAllPosts;
	public Person user;

	public static UserDAO _instance = null;
	public String localPath;

	private UserDAO(String path) {
		// TODO Auto-generated constructor stub
		try {
			localPath = path;
			currentCon = ConnectionManager.getConnection(path);

			stmt = currentCon.createStatement();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static UserDAO getInstace(String contextPath) {
		// TODO Auto-generated method stub
		if (_instance == null)
			_instance = new UserDAO(contextPath);
		return _instance;

	}
	// 104.155.16.11:8080/facebook-replica/wall

	public void CloseAll() {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
			rs = null;
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
			}
			stmt = null;
		}

		if (currentCon != null) {
			try {
				currentCon.close();
			} catch (Exception e) {
			}

			currentCon = null;
		}

	}

	public boolean SignUp(String firstName, String lastName, Date dateOfBirth, boolean sex, String email,
			String password, String place, String website, String education, String occupation, String employment,
			String picture) {
		// extra query + race condition but coulnd't catch the exception being
		// thrown
		boolean succesFlag = false;
		try {
			// Check if User Already Exists.
			if (this.user != null)
				return succesFlag;

			// Create FriendList

			int FriendList_id = -1;
			if (stmt.executeUpdate("insert into FriendList values (null,0)") > 0) {
				// a. Get Likes_id
				ResultSet rs11 = stmt
						.executeQuery("SELECT * FROM FriendList ORDER BY FriendList.friendlist_id DESC LIMIT 1;");
				if (rs11.next()) {
					FriendList_id = rs11.getInt("friendlist_id");
					// We are good to go

					PreparedStatement pstmt = this.currentCon
							.prepareStatement("insert into Person values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					// String inserQuery = " Insert into Person (firstname , sex
					// , email
					// ,
					// password ) values
					// ('aaa' , 'male' , 'aaa@sadas.cm' , 3216549987 )

					int i = 2;
					// pstmt.setInt(i++, 56);
					pstmt.setString(i++, firstName);
					pstmt.setString(i++, lastName);
					pstmt.setInt(i++, sex ? 1 : 0);
					pstmt.setString(i++, email);
					pstmt.setString(i++, password); // password
					pstmt.setString(i++, dateOfBirth.toString());
					pstmt.setString(i++, place);
					pstmt.setString(i++, website);
					pstmt.setString(i++, education);
					pstmt.setString(i++, occupation);
					pstmt.setString(i++, employment);
					pstmt.setString(i++, picture);
					pstmt.setInt(i++, FriendList_id);

					int a = pstmt.executeUpdate();
					if (a > 0) {

						if (picture == null)
							picture = "person.gif";
						rs = stmt.executeQuery("select Person.person_id from Person " + "where Person.firstname='"
								+ firstName + "' AND Person.lastname='" + lastName + "'  AND Person.email='" + email
								+ "' ");
						if (rs.next()) {
							int Id = rs.getInt("person_id");

							Person person = new Person();
							person.setId(Id);
							person.setFirstName(firstName);
							person.setLastName(lastName);
							person.setDateOfBirth(dateOfBirth);
							person.setSex(sex);
							person.setEmail(email);
							person.setPlace(place);
							person.setWebsite(website);
							person.setEducation(education);
							person.setOccupation(occupation);
							person.setEmployment(employment);
							person.setPicture(picture);
							person.setPassword(password);
							person.setFriendListId(FriendList_id);

							this.user = person;
							succesFlag = true;
							System.out.println("Values Are Inserted");
						}
					}
				}

			} else {
				System.out.println("Values Are not Inserted");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			succesFlag = false;
		}

		return succesFlag;

	}

	public UserBean login(UserBean bean) {

		// preparing some objects for connection

		String username = bean.getUsername();
		String password = bean.getPassword();

		String searchQuery = "select * from Person where name='" + username + "' AND password='" + password + "'";

		// "System.out.println" prints in the console; Normally used to trace
		// the process
		System.out.println("Your user name is " + username);
		System.out.println("Your password is " + password);
		System.out.println("Query: " + searchQuery);

		try {

			rs = stmt.executeQuery(searchQuery);
			boolean more = rs.next();
			// if user does not exist set the isValid variable to false
			if (!more) {
				System.out.println("Sorry, you are not a registered user! Please sign up first");
				bean.setValid(false);
			}

			// if user exists set the isValid variable to true
			else if (more) {
				String firstName = rs.getString("name");
				String lastName = rs.getString("email");

				System.out.println("Welcome " + firstName);
				bean.setFirstName(firstName);
				bean.setLastName(lastName);
				bean.setValid(true);
				bean.setValue(randomUid());
			}
		}

		catch (Exception ex) {
			bean.setValid(false);
			System.out.println("Log In failed: An Exception has occurred! " + ex);
		}

		// some exception handling
		// finally {
		// if (rs != null) {
		// try {
		// rs.close();
		// } catch (Exception e) {
		// }
		// rs = null;
		// }
		//
		// if (stmt != null) {
		// try {
		// stmt.close();
		// } catch (Exception e) {
		// }
		// stmt = null;
		// }
		//
		// if (currentCon != null) {
		// try {
		// currentCon.close();
		// } catch (Exception e) {
		// }
		//
		// currentCon = null;
		// }
		// }

		return bean;

	}

	public Person login(String email, String password) {

		// preparing some objects for connection
		boolean success = false;
		if (this.currentCon == null) {
			// Already Login.
			UserDAO.getInstace(localPath);
			this.currentCon = UserDAO._instance.currentCon;
		}

		String searchQuery = "select * from Person where email='" + email + "' AND password='" + password + "'";

		try {

			ResultSet rss = currentCon.createStatement().executeQuery(searchQuery);
			boolean more = rss.next();
			// if user does not exist set the isValid variable to false
			if (!more) {
				System.out.println("Sorry, you are not a registered user! Please sign up first");
				success = false;
			}

			// if user exists set the isValid variable to true
			else if (more) {

				// Create a Person Here.

				this.user = new Person();
				this.user.setId(rss.getInt("person_id"));
				this.user.setFirstName(rss.getString("firstname"));
				this.user.setLastName(rss.getString("lastname"));
				this.user.setDateOfBirth(rss.getString("dob"));
				this.user.setSex(rss.getInt("sex") == 1 ? true : false);
				this.user.setEmail(rss.getString("email"));
				this.user.setPlace(rss.getString("place"));
				this.user.setWebsite(rss.getString("website"));
				this.user.setEducation(rss.getString("education"));
				this.user.setOccupation(rss.getString("occupation"));
				this.user.setEmployment(rss.getString("employment"));
				this.user.setPicture(rss.getString("photo"));
				this.user.setPassword(rss.getString("password"));
				this.user.setFriendListId(rss.getInt("friend_list"));

				success = true;
			}
		}

		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Log In failed: An Exception has occurred! " + ex);
			success = false;
		}
		return this.user;

	}

	public Person GetPerson(int id) {

		// preparing some objects for connection
		boolean success = false;
		Person somePerson = null;
		String searchQuery = "select * from Person where person_id='" + id + "' ";

		try {
			Statement some = currentCon.createStatement();
			ResultSet rss = some.executeQuery(searchQuery);
			boolean more = rss.next();
			// if user does not exist set the isValid variable to false
			if (!more) {
				System.out.println("Sorry, you are not a registered user! Please sign up first");
				success = false;
			}

			// if user exists set the isValid variable to true
			else if (more) {

				// Create a Person Here.

				somePerson = new Person();
				somePerson.setId(rss.getInt("person_id"));
				somePerson.setFirstName(rss.getString("firstname"));
				somePerson.setLastName(rss.getString("lastname"));
				somePerson.setDateOfBirth(rss.getString("dob"));
				somePerson.setSex(rss.getInt("sex") == 1 ? true : false);
				somePerson.setEmail(rss.getString("email"));
				somePerson.setPlace(rss.getString("place"));
				somePerson.setWebsite(rss.getString("website"));
				somePerson.setEducation(rss.getString("education"));
				somePerson.setOccupation(rss.getString("occupation"));
				somePerson.setEmployment(rss.getString("employment"));
				somePerson.setPicture(rss.getString("photo"));
				somePerson.setPassword(rss.getString("password"));
				somePerson.setFriendListId(rss.getInt("friend_list"));

				success = true;
			}
		}

		catch (Exception ex) {

			System.out.println("Log In failed: An Exception has occurred! " + ex);
			success = false;
		}
		return somePerson;

	}

	public static int randomUid() {
		return (int) Math.random();

	}

	public List<Person> findAllSortedByPlace(boolean order) {
		// TODO Auto-generated method stub

		// Query q = em.createQuery("SELECT p FROM Person p WHERE
		// p.password=:password");

		List<Person> personArray = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM Person");
			while (rs.next()) {
				Person person = new Person();
				person.setFirstName(rs.getString("name"));
				person.setEmail(rs.getString("email"));
				System.out.println(person.getFirstName() + " , " + person.getEmail());
				// person.setSex( rs.getString("gender") );
				personArray.add(person);
			}

		} catch (Exception e) {
			// handle exception here
			personArray = null;
		}
		return personArray;

	}

	public List<Person> findAllSortedByDateOfBirth(boolean order) {
		// TODO Auto-generated method stub
		List<Person> personArray = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM Person");
			while (rs.next()) {
				Person person = new Person();
				person.setFirstName(rs.getString("name"));
				person.setEmail(rs.getString("email"));

				// person.setSex( rs.getString("gender") );
				personArray.add(person);
			}

		} catch (Exception e) {
			// handle exception here
			personArray = null;
		}
		return personArray;
	}

	public List<Person> findAllSortedByName(boolean order, int personId) {
		// TODO Auto-generated method stub
		List<Person> personArray = new ArrayList<Person>();
		try {
			// stmt = currentCon.createStatement();
			// rs = stmt.executeQuery("select * from Person");

			String searchQuery = "select * from Person";
			Statement currstmt = currentCon.createStatement();
			ResultSet rss = currstmt.executeQuery(searchQuery);
			while (rss.next()) {

				Person somePerson = new Person();

				somePerson.setId(rss.getInt("person_id"));
				somePerson.setFirstName(rss.getString("firstname"));
				somePerson.setLastName(rss.getString("lastname"));
				somePerson.setDateOfBirth(rss.getString("dob"));
				somePerson.setSex(rss.getInt("sex") == 1 ? true : false);
				somePerson.setEmail(rss.getString("email"));
				somePerson.setPlace(rss.getString("place"));
				somePerson.setWebsite(rss.getString("website"));
				somePerson.setEducation(rss.getString("education"));
				somePerson.setOccupation(rss.getString("occupation"));
				somePerson.setEmployment(rss.getString("employment"));
				somePerson.setPicture(rss.getString("photo"));
				somePerson.setPassword(rss.getString("password"));
				somePerson.setFriendListId(rss.getInt("friend_list"));
				// person.setSex( rs.getString("gender") );
				personArray.add(somePerson);
			}

			List<Person> friendList = GetFriends(personId, Data.ALL);
			// Cross checking and marking the friends in personArray

			if (friendList != null && personArray != null && personArray.size() > 0) {
				for (int i = 0; i < friendList.size(); i++) {
					Person temp = friendList.get(i);
					for (int j = 0; j < personArray.size(); j++) {
						if (temp.getId().intValue() == personArray.get(j).getId().intValue())
							personArray.get(j).setStatus(temp.getStatus());
					}

				}
			}

		} catch (Exception e) {
			// handle exception here
			e.printStackTrace();
			personArray = null;
		}
		return personArray;
	}

	public List<Post> findAllPost() {

		List<Post> postArray = new ArrayList<Post>();
		try {
			// stmt = currentCon.createStatement();
			// rs = stmt.executeQuery("select * from Person");

			String searchQuery = "select * from Post";
			
			rs = currentCon.createStatement().executeQuery(searchQuery);
			// 1: Get All Post
			while (rs.next()) {
				Post post = new Post();
				post.setId(rs.getInt("post_id"));

				int PersonId = (rs.getInt("person_id"));
				int FileId = (rs.getInt("file_id"));
				int LikesId = (rs.getInt("likes_id"));

				post.setPerson_id(PersonId);

				// 2: Get Likers ID and count them.
				ResultSet rs2 = currentCon.createStatement().executeQuery("select Liker.person_id from Likes " + "join Liker "
						+ "on Likes.liker_id = Liker.liker_id " + "  where Likes.likes_id = " + LikesId);
				List<Integer> Likers = new ArrayList<>();
				while (rs2.next()) {
					if (rs2.getInt("person_id") != 0)
						Likers.add(rs2.getInt("person_id"));
				}

				post.setLikers(Likers);

				// 2. Get File Against them
				Statement statement2 = currentCon.createStatement();
				ResultSet rs1 = statement2.executeQuery("select * from File where file_id=" + FileId);

				while (rs1.next()) {
					CFile file = new CFile();
					file.setId(rs1.getInt("file_id"));

					file.setVideos(rs1.getString("video"));
					file.setText(rs1.getString("text"));
					file.setVideos(rs1.getString("image"));

					// Loading Image if theres any.
					// byte[] imgArr = rs1.getBytes("image");
					// if (imgArr != null) {
					// // Todo: Use Relative path
					//
					// String fileLoc = localPath + "/image/image_" +
					// file.getId() + ".jpg";
					// // String fileLoc =
					// // "D:\\J2EE\\Facebook-Replica\\resources\\image" + "\\"
					// // + "image_" + file.getId()
					//
					// // File imagePath = new File(path +
					// // "\\resources\\image\\");
					// try {
					// FileOutputStream fos = new FileOutputStream(fileLoc);
					// fos.write(imgArr);
					// String ppath = fos.getFD().toString();
					//
					// fos.close();
					//
					// file.setImage(fileLoc);
					//
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
					// }
					file.setFormattedDate(new Data().toString());
					file.setTitle(post.getText());

					post.setFile(file);
					
					
					post.setPerson(GetPerson(PersonId));
				}

				// 4: Get the Person who posted this.
				// PersonId
				postArray.add(post);

			}
			listOfAllPosts = postArray;
		} catch (Exception e) {
			// handle exception here
			e.printStackTrace();
			postArray = null;
		}
		return postArray;
	}

	public List<Post> PreferentialSearch() {

		List<Post> list = findAllPost();
		if (list != null) {
			Collections.sort(list);
		}

		// int index[] = new int[list.size()];
		// if(list != null)
		// {
		// int l = list.size();
		// int maxLike = 0;
		// int changeIndex = 0;
		// for (int i = 0; i < l ; i++) {
		// maxLike = list.get(i).getMaxLikes();
		// changeIndex = i;
		// for(int j= i+1 ; j < l ; j++)
		// {
		// if(list.get(j).getMaxLikes() > maxLike)
		// {
		// maxLike = list.get(j).getMaxLikes();
		// changeIndex = j;
		// }
		//
		// }
		//
		// // Lets swap here.
		// index[i] = maxLike;
		// }
		// }

		return list;

	}

	public boolean addALike(Integer postId, Integer ownerId) {
		// TODO Auto-generated method stub

		boolean flag = false;
		// Todo : when session is maintained.

		// 1. Get PostID and Flag only.
		// 2. Search in the local list where postid = postID
		// 3.
		// String searchQuery = "SELECT person_id FROM Post " + " where
		// Post.post_id = " + postId
		// + " ORDER BY Post.post_id ASC LIMIT 1 ";

		try {

			// ResultSet rss =
			// currentCon.createStatement().executeQuery(searchQuery);
			//
			// if (rss.next()) {
			// int personOwnerId = rss.getInt("person_id");

			String insertQuery = " INSERT INTO Liker( liker_id, person_id) " + " select lk.liker_id, " + ownerId
					+ " as person_id from Post p " + " join Likes l " + " on p.likes_id = l.likes_id "
					+ " join Liker lk " + " on l.liker_id=lk.liker_id " + " where p.post_id=" + postId
					+ " group by lk.liker_id";

			int indexNum = currentCon.createStatement().executeUpdate(insertQuery);
			System.out.println(indexNum + " --- - ");
			if (indexNum > 0)
				flag = true;
			// }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			flag = false;
		}

		return flag;

	}

	public boolean CreateAPost(String title, String text, Integer personId, Integer ownerId, String pictureFilename,
			String youtubeVideoId, String link) {

		boolean successFlag = false;

		try {
			// This is a test
			// 1. Create File
			PreparedStatement pstmt = this.currentCon.prepareStatement("insert into File values(?,?,?,?)");

			int i = 2;
			pstmt.setString(i++, youtubeVideoId);
			pstmt.setString(i++, text);
			pstmt.setString(i++, pictureFilename);

			int a = pstmt.executeUpdate();
			if (a > 0) {

				System.out.println("File has Created");

				// a. Get File_id
				rs = stmt.executeQuery("SELECT * FROM File ORDER BY file_id DESC LIMIT 1;");
				if (rs.next()) {
					int file_Id = rs.getInt("file_id");
					String fileText = rs.getString("text"), video = rs.getString("video");

					// Part one completed

					// 2. Create Likes
					int likeCreated = stmt.executeUpdate("insert into Likes values (null, 100);");
					int likes_id = -1;
					if (likeCreated > 0) {
						// a. Get Likes_id
						ResultSet rs1 = stmt.executeQuery("SELECT * FROM Likes ORDER BY likes_id DESC LIMIT 1;");
						if (rs1.next()) {
							likes_id = rs1.getInt("likes_id");
							// We are good to go

							PreparedStatement postCreaterStatment = this.currentCon
									.prepareStatement("insert into Post values(?,?,?,?)");

							int j = 2;
							postCreaterStatment.setInt(j++, personId);
							postCreaterStatment.setInt(j++, file_Id);
							postCreaterStatment.setInt(j++, likes_id);

							if (postCreaterStatment.executeUpdate() > 0) {

								ResultSet rs2 = stmt.executeQuery("SELECT * FROM Post ORDER BY post_id DESC LIMIT 1;");
								if (rs2.next()) {
									Post post = new Post();
									post.setId(rs2.getInt("post_id"));

									post.setLikes(likes_id);
									post.setPerson_id(personId);
									post.setPerson(this.user);
									CFile file = new CFile();
									file.setId(file_Id);
									// file.setImage(rs.getString("image"));
									file.setText(fileText);
									file.setVideos(video);
									file.setTitle(title);
									file.setFormattedDate(file.getFormattedDate());

									post.setFile(file);

									// Adding it to the current user
									this.listOfAllPosts.add(post);
									successFlag = true;
								}

							}
						}

					}
				}
			}
			// 2. Create Likes
			// a. Get Likes_id
			// 3. Create Post

			// 1. Create File
			// a. Get File_id

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			successFlag = false;
		}

		return successFlag;
	}

	public List<Person> GetFriends(int personID, int Filter) {
		// TODO Auto-generated method stub

		List<Person> friendList = new ArrayList<>();
		String friendFinder = "select  (per.person_id), frnd.status " + " from Person p  " + "	join FriendList flist "
				+ "        on flist.friendlist_id =  p.friend_list " + "	join Friends frnd  "
				+ "		on frnd.friends_id = flist.friends_id " + "	join Person per  "
				+ "		on per.person_id=frnd.person_id " + "	where p.person_id= " + personID + " ;";

		try {

			Statement curr = currentCon.createStatement();
			ResultSet rsss = curr.executeQuery(friendFinder);
			int someSize = rsss.getFetchSize();
			// 1: Get All Post
			while (rsss.next()) {
				int friendID = rsss.getInt("person_id");
				int status = rsss.getInt("status");
				if (status == Filter || Filter == Data.ALL) {
					Person person = GetPerson(friendID);
					person.setStatus(status);
					friendList.add(person);
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return friendList;
	}

	public boolean sendFriendRequest(Integer friendId, Integer personId) {
		// TODO Auto-generated method stub

		boolean flagOfsuccess = false;

		try {
			String strr = "select  * from Friends where Friends.person_id = " + personId;
			ResultSet sett = currentCon.createStatement().executeQuery(strr);
			System.out.println("friends_id, person_id, status");
			while (sett.next()) {
				System.out.println(sett.getString("friends_id") + " , " + sett.getString("person_id") + " , "
						+ sett.getString("status"));
			}

			// Statement curr = currentCon.createStatement();
			// ResultSet result = curr.executeQuery(selectSpecificPeople);
			// if (result.next()) {
			// int friendlist = result.getInt("friends_id");
			// String insertTheFriendRequest = " insert into Friends (
			// friends_id , status , person_id ) values ( "
			// + friendlist + " , " + Data.PENDING + " , " + personId + " ) ";

			String insertTheFriendRequest = " replace   into Friends ( friends_id , status , person_id )  values ( "
					+ "		 (select   fList.friends_id 	from Person p " + "			 join FriendList fList  "
					+ "			 on fList.friendlist_id =  p.friend_list " + "			  where p.person_id = "
					+ friendId + " ), " + "		 " + Data.PENDING + " , " + "		 " + personId + " ) ;";
			int as = currentCon.createStatement().executeUpdate(insertTheFriendRequest);
			if (as > 0) {
				flagOfsuccess = true;

				// Just a test
				String str = "select  * from Friends where Friends.person_id = " + personId;
				ResultSet set = currentCon.createStatement().executeQuery(str);
				System.out.println("friends_id, person_id, status");
				while (set.next()) {
					System.out.println(set.getString("friends_id") + " , " + set.getString("person_id") + " , "
							+ set.getString("status"));
				}
			}

			// }

		} catch (Exception ex) {
			ex.printStackTrace();
			flagOfsuccess = false;
		}
		return flagOfsuccess;
	}

	// select fList.friends_id from Person p join FriendList fList on
	// fList.friendlist_id = p.friend_list where p.person_id = 1
	public boolean acceptFriendRequest(Integer friendId, Integer personId) {
		// TODO Auto-generated method stub

		String friendFinder = "UPDATE  Friends " + " SET status = " + Data.FRIENDS + " "
				+ " WHERE  person_id = (select frnd.person_id from Person p " + "			join FriendList flist "
				+ "				       on flist.friendlist_id =  p.friend_list " + "			join Friends frnd "
				+ "						on frnd.friends_id = flist.friends_id 	" + "			join Person per  "
				+ "					on per.person_id=frnd.person_id 	" + "	where  p.person_id = " + personId
				+ "  AND per.person_id = " + friendId + "  AND  frnd.status == " + Data.PENDING + " )";

		boolean flagOfsuccess = false;
		try {

			Statement curr = currentCon.createStatement();
			int result = curr.executeUpdate(friendFinder);
			if (result > 0)
				flagOfsuccess = true;
		} catch (Exception ex) {
			flagOfsuccess = false;
		}
		return flagOfsuccess;
	}

	public boolean removeFriendRequest(Integer friendId, Integer personId) {
		// TODO Auto-generated method stub
		String friendFinder = "DELETE  FROM Friends "
				+ "WHERE  Friends.person_id = (select per.person_id from Person p " + "join FriendList flist "
				+ "	       on flist.friendlist_id =  p.friend_list " + "join Friends frnd "
				+ "			on frnd.friends_id = flist.friends_id 	" + "join Person per  "
				+ "		on per.person_id=frnd.person_id " + "where  p.person_id=" + personId + "  AND per.person_id = "
				+ friendId + " );";

		boolean flagOfsuccess = false;
		try {

			Statement curr = currentCon.createStatement();
			int result = curr.executeUpdate(friendFinder);
			if (result > 0)
				flagOfsuccess = true;

			flagOfsuccess = true;
		} catch (Exception ex) {
			flagOfsuccess = false;
		}
		return flagOfsuccess;

	}

	// when current user share some one post.
	public boolean sharePost(Integer postId, Integer personId) {
		// TODO Auto-generated method stub.
		boolean flag = false;
		if (this.listOfAllPosts != null) {
			for (Post p : this.listOfAllPosts) {
				CFile file = p.getFile();
				if (p.getId().intValue() == postId.intValue() && file != null) {
					if (CreateAPost(file.getTitle(), file.getText(), personId, null, file.getImage(), file.getVideos(),
							file.getText())) {
						flag = true;
					}
					break;
				}

			}
		}

		return flag;
	}

}

// - See more at:
// http://www.codemiles.com/jsp-examples/login-using-jsp-servlets-and-database-following-mvc-t10898.html#sthash.erjdhXpE.dpuf