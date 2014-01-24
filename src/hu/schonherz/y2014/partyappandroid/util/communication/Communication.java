package hu.schonherz.y2014.partyappandroid.util.communication;

import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.OwnerRequest;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.StrictMode;
import android.util.Log;

public class Communication implements CommunicationInterface {

	HttpClient httpclient;

	final String MainURL = "http://partyapp.bugs3.com/";

	public Communication() {
		httpclient = new DefaultHttpClient();
	}

	public String httpPost(String file, HashMap<String, String> post)
			throws ClientProtocolException, IOException {
		/* Ideiglenes megoldás - vtms */
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		Iterator it = post.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			nameValuePairs.add(new BasicNameValuePair((String) pairs.getKey(),
					(String) pairs.getValue()));
		}

		HttpPost httppost = new HttpPost(MainURL + file);
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);
		String data = new BasicResponseHandler().handleResponse(response);
		return data;
	}

	// kész
	@Override
	public User authenticationUser(String nickname, String password) {
		HashMap<String, String> post = new HashMap<String, String>();
		post.put("action", "GET");
		post.put("NickName", nickname);
		post.put("Password", password);

		try {
			String data = httpPost("user.php", post);
			JSONArray jsonArray = new JSONArray(data);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			int user_id = Integer.parseInt(jsonObject.getString("id"));
			String nick_name2 = jsonObject.getString("nick_name");
			String password2 = jsonObject.getString("password");
			String email = jsonObject.getString("email");
			int sex = Integer.parseInt(jsonObject.getString("sex"));
			String birthday = jsonObject.getString("birthday");
			int type = Integer.parseInt(jsonObject.getString("type"));

			User newUser = new User(user_id, nick_name2, password2, email, sex,
					birthday, type);
			return newUser;
		} catch (Exception e) {

		}

		return null;
	}

	@Override
	public List<Club> getFavoriteClubsFromUserId(int user_id) {
		ArrayList<Club> outList = new ArrayList<Club>();
		return outList;
	}

	// kész
	@Override
	public List<Club> getClubsFromCityName(String cityname) {
		try {
			return searchClubs("", cityname, "", 0, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Club> getOwnedClubsFromUserId(int user_id) {
		ArrayList<Club> outList = new ArrayList<Club>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("action", "GETUSERCLUBS"));
		nameValuePairs.add(new BasicNameValuePair("UserID", (new Integer(
				user_id)).toString()));

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(MainURL + "club.php");

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
			if (data.equals("FAILED")) {
				return outList;
			} else {
				JSONArray jsonArray = new JSONArray(data);
				for (int i = 0; i < jsonArray.length(); ++i) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					int clubId = jsonObject.getInt("id");
					String clubName = jsonObject.getString("name");
					String clubAddress = jsonObject.getString("address");
					Club newClub = new Club(clubId, clubName, clubAddress);
					outList.add(newClub);
				}

			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return outList;
	}

	@Override
	public Club getEverythingFromClub(int club_id) {
		HashMap<String, String> post = new HashMap<String, String>();
		post.put("action", "GETBYID");
		post.put("id", String.valueOf(club_id));

		try {
			String data = httpPost("club.php", post);

			Log.i("asdasd", data);

			JSONArray jsonArray = new JSONArray(data);
			JSONObject j = jsonArray.getJSONObject(0);

			return new Club(j.getInt("id"), j.getString("name"),
					j.getString("type"), j.getString("description"),
					j.getString("address"), j.getString("phonenumber"),
					j.getString("email"), "nemtudommilyendátum",
					j.getString("highlight_expire"), j.getInt("approved"));
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "bajvan", e);
		}

		return null;
	}

	@Override
	public void sendANewClubRequest(String newClubName, String newClubAddress,
			String newClubType, int owner_user_id, List<String> services) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("action", "ADD"));
		nameValuePairs.add(new BasicNameValuePair("Name", newClubName));
		nameValuePairs.add(new BasicNameValuePair("Type", newClubType));
		nameValuePairs.add(new BasicNameValuePair("Address", newClubAddress));

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(MainURL + "club.php");

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
			try {
				if (data.equals("FAILED")) {
					// itt baj lesz
				} else {
					JSONObject jsonObject = new JSONObject(data);
					int club_id = Integer.parseInt(jsonObject
							.getString("NewID"));
					setServices(club_id, services);
					if (owner_user_id != -1) {
						setOwnerForClub(owner_user_id, club_id);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<Club> searchClubs(String name, String cityname, String type,
			int offset, int limit) throws Exception {

		try {
			HashMap<String, String> post = new HashMap<String, String>();
			post.put("action", "SEARCH");
			post.put("name", name);
			post.put("cityname", cityname);
			post.put("type", type);
			post.put("offset", String.valueOf(offset));
			post.put("limit", String.valueOf(limit));

			List<Club> ret = new LinkedList<Club>();
			String data = httpPost("club.php", post);
			JSONArray jsonArray = new JSONArray(data);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				ret.add(new Club(jsonObject.getInt("id"), jsonObject
						.getString("name"), jsonObject.getString("address")));
			}
			return ret;
		} catch (Exception e) {

		}

		return null;

	}

	// kész
	@Override
	public void modifyPassword(int id, String password) throws Exception {
		try {
			HashMap<String, String> post = new HashMap<String, String>();
			post.put("action", "UPDATEPASSWORD");
			post.put("userid", String.valueOf(id));
			post.put("password", password);

			String data = httpPost("user.php", post);

		} catch (Exception e) {

		}
	}

	// kész
	@Override
	public void modifyUserData(int id, String email, String birthday, int sex)
			throws Exception {

		try {
			HashMap<String, String> post = new HashMap<String, String>();
			post.put("action", "UPDATEUSER");
			post.put("UserID", String.valueOf(id));
			post.put("Email", email);
			post.put("Sex", String.valueOf(sex));
			post.put("Birthday", birthday);

			String data = httpPost("user.php", post);

		} catch (Exception e) {

		}
	}

	@Override
	public User registerANewUser(String nick_name, String password,
			String email, int sex, String birthday) {

		HashMap<String, String> post = new HashMap<String, String>();
		post.put("action", "ADD");
		post.put("NickName", nick_name);
		post.put("Password", password);
		post.put("Email", email);
		post.put("Sex", String.valueOf(sex));
		post.put("Birthday", birthday);

		try {
			String data = httpPost("user.php", post);

			if (data.equals("FAILED")) {
				return null;
			}
			JSONObject jsonObject = new JSONObject(data);
			int user_id = Integer.parseInt(jsonObject.getString("NewID"));
			User newUser = new User(user_id, nick_name, password, email, sex,
					birthday, 0);
			return newUser;
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public void setServices(int club_id, List<String> services) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		for (String actualService : services) {

			nameValuePairs.add(new BasicNameValuePair("action", "ADD"));
			nameValuePairs.add(new BasicNameValuePair("ClubID", (new Integer(
					club_id)).toString()));
			nameValuePairs.add(new BasicNameValuePair("Type", actualService));

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(MainURL + "service.php");

			try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
						"UTF-8"));
				HttpResponse response = httpclient.execute(httppost);
				String data = new BasicResponseHandler()
						.handleResponse(response);
				try {
					if (data.equals("FAILED")) {
						// itt baj lesz
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void setOwnerForClub(int user_id, int club_id) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("action", "ADD"));
		nameValuePairs.add(new BasicNameValuePair("UserID", (new Integer(
				user_id)).toString()));
		nameValuePairs.add(new BasicNameValuePair("ClubID", (new Integer(
				club_id)).toString()));
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(MainURL + "owner.php");

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
			try {
				if (data.equals("FAILED")) {
					// itt baj lesz
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setFavoriteClubForUser(int user_id, int club_id) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("action", "ADD"));
		nameValuePairs.add(new BasicNameValuePair("UserID", (new Integer(
				user_id)).toString()));
		nameValuePairs.add(new BasicNameValuePair("ClubID", (new Integer(
				club_id)).toString()));

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(MainURL + "favorite.php");
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
			try {
				if (data.equals("FAILED")) {
					// itt baj lesz
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteFavoriteClubForUser(int fav_id) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("action", "DELETE"));
		nameValuePairs.add(new BasicNameValuePair("FavID",
				(new Integer(fav_id)).toString()));

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(MainURL + "favorite.php");
		try {

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
			if (!data.equals("OK")) {
				// bajvan
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<Club> getNotApprovedClubs() {
		try {
			HashMap<String, String> post = new HashMap<String, String>();
			post.put("action", "GETNOTAPPROVEDCLUB");

			List<Club> ret = new LinkedList<Club>();
			String data = httpPost("club.php", post);
			JSONArray jsonArray = new JSONArray(data);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				ret.add(new Club(jsonObject.getInt("id"), jsonObject
						.getString("name"), jsonObject.getString("address")));
			}
			return ret;
		} catch (Exception e) {

		}

		return new LinkedList<Club>();
	}

	@Override
	public void approveClub(int club_id) {
		try {
			HashMap<String, String> post = new HashMap<String, String>();
			post.put("action", "ACCEPTCLUB");
			post.put("clubid", String.valueOf(club_id));

			String data = httpPost("club.php", post);

		} catch (Exception e) {

		}
		
	}

	@Override
	public void declineNewClub(int club_id) {
		try {
			HashMap<String, String> post = new HashMap<String, String>();
			post.put("action", "DECLINECLUB");
			post.put("clubid", String.valueOf(club_id));

			String data = httpPost("club.php", post);

		} catch (Exception e) {

		}
		
	}

	@Override
	public void declineOwnerRequest(int club_id, int user_id) {
		try {
			HashMap<String, String> post = new HashMap<String, String>();
			post.put("action", "DELETE");
			post.put("clubid", String.valueOf(club_id));
			post.put("userid", String.valueOf(user_id));

			String data = httpPost("owner.php", post);

		} catch (Exception e) {

		}
		
	}

	@Override
	public List<OwnerRequest> getNotApprovedOwnerRequest() {
		try {
			HashMap<String, String> post = new HashMap<String, String>();
			post.put("action", "GET");

			List<OwnerRequest> ret = new LinkedList<OwnerRequest>();
			String data = httpPost("owner.php", post);
			Log.i("itt",data);
			JSONArray jsonArray = new JSONArray(data);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				ret.add(new OwnerRequest(new Club(jsonObject.getInt("club_id"), jsonObject
						.getString("name"), jsonObject.getString("address")),new User(jsonObject.getInt("user_id"), jsonObject.getString("nick_name"), null, jsonObject.getString("email"), 0, null, 0)));
			}
			return ret;
		} catch (Exception e) {

		}

		return new LinkedList<OwnerRequest>();
	}

	@Override
	public void acceptOwnerRequest(int club_id, int user_id) {
		try {
			HashMap<String, String> post = new HashMap<String, String>();
			post.put("action", "ACCEPT");
			post.put("clubid", String.valueOf(club_id));
			post.put("userid", String.valueOf(user_id));

			String data = httpPost("owner.php", post);

		} catch (Exception e) {

		}
		
	}

}
