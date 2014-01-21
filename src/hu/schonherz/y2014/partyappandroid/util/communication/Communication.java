package hu.schonherz.y2014.partyappandroid.util.communication;

import hu.schonherz.y2014.partyappandroid.util.datamodell.Club;
import hu.schonherz.y2014.partyappandroid.util.datamodell.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

public class Communication implements CommunicationInterface{
	
	HttpClient httpclient;
	
	public Communication() {
		httpclient = new DefaultHttpClient();
	}
	
	@Override
	public User authenticationUser(String nickname, String password) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	    nameValuePairs.add(new BasicNameValuePair("action", "GET"));
	    nameValuePairs.add(new BasicNameValuePair("NickName", nickname));
	    nameValuePairs.add(new BasicNameValuePair("Password", password));
	    
	    HttpPost httppost = new HttpPost("http://schonhercz.bl.ee/partyapp/user.php");  
	    try {
	    	
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
			if(data.equals("FAILED")){
				return null;
			}
			JSONArray jsonArray = new JSONArray(data);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
        	int user_id = Integer.parseInt(jsonObject.getString("id"));
        	jsonObject = jsonArray.getJSONObject(1);
        	String nick_name = jsonObject.getString("nick_name");
        	jsonObject = jsonArray.getJSONObject(2);
        	String password2 = jsonObject.getString("password");
        	jsonObject = jsonArray.getJSONObject(3);
        	String email = jsonObject.getString("email");
        	jsonObject = jsonArray.getJSONObject(4);
        	int sex = Integer.parseInt(jsonObject.getString("sex"));
        	jsonObject = jsonArray.getJSONObject(5);
        	String birthday = jsonObject.getString("birthday");
        	jsonObject = jsonArray.getJSONObject(6);
        	int type = Integer.parseInt(jsonObject.getString("type"));
        	
        	User newUser = new User(user_id, nick_name, password2, email, sex, birthday, type);
        	return newUser;
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
	    return null;
	}

	@Override
	public List<Club> getFavoriteClubsFromUserId(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Club> getClubsFromCityName(String cityname) {
		ArrayList<Club> outList = new ArrayList<Club>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	    nameValuePairs.add(new BasicNameValuePair("action", "GETFROMCITYNAME"));
	    nameValuePairs.add(new BasicNameValuePair("CityName", cityname));
	    
	    HttpClient httpclient = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost("http://schonhercz.bl.ee/partyapp/club.php");  
		
	    try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
			if(data.equals("FAILED")){
				return null;
			} else {
				JSONArray jsonArray = new JSONArray(data);
				for(int i=0; i<jsonArray.length(); ++i){
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
	public List<Club> getOwnedClubsFromUserId(int user_id) {
		ArrayList<Club> outList = new ArrayList<Club>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	    nameValuePairs.add(new BasicNameValuePair("action", "GETUSERCLUBS"));
	    nameValuePairs.add(new BasicNameValuePair("UserID", (new Integer(user_id)).toString()));
	    
	    HttpClient httpclient = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost("http://schonhercz.bl.ee/partyapp/club.php");  
		
	    try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
			if(data.equals("FAILED")){
				return null;
			} else {
				JSONArray jsonArray = new JSONArray(data);
				for(int i=0; i<jsonArray.length(); ++i){
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendANewClubRequest(String newClubName, String newClubAddress,
			String newClubType, int owner_user_id, String[] services) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	    nameValuePairs.add(new BasicNameValuePair("action", "ADD"));
	    nameValuePairs.add(new BasicNameValuePair("Name", newClubName));
	    nameValuePairs.add(new BasicNameValuePair("Type", newClubType));
	    nameValuePairs.add(new BasicNameValuePair("Address", newClubAddress));
		
	    HttpClient httpclient = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost("http://schonhercz.bl.ee/partyapp/club.php");   

	    try {
	    	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
	        try {
	            if(data.equals("FAILED")){
	    			//itt baj lesz
	    		} else {
	    			JSONObject jsonObject = new JSONObject(data);
	            	int club_id = Integer.parseInt(jsonObject.getString("NewID"));
	    			setServices(club_id, services);
	    			if(owner_user_id != -1){
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyPassword(int id, String password) throws Exception {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	    nameValuePairs.add(new BasicNameValuePair("action", "UPDATEPASSWORD"));
	    nameValuePairs.add(new BasicNameValuePair("UserID", (new Integer(id)).toString()));
	    nameValuePairs.add(new BasicNameValuePair("Password", password));
	    
	    HttpPost httppost = new HttpPost("http://schonhercz.bl.ee/partyapp/user.php");  
	    try {
	    	
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
			if(!data.equals("OK")){
				throw new Exception();
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
	}

	@Override
	public void modifyUserData(int id, String email, String birthday, int sex)
			throws Exception {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	    nameValuePairs.add(new BasicNameValuePair("action", "UPDATEUSER"));
	    nameValuePairs.add(new BasicNameValuePair("UserID", (new Integer(id)).toString()));
	    nameValuePairs.add(new BasicNameValuePair("Email", email));
	    nameValuePairs.add(new BasicNameValuePair("Sex", (new Integer(sex)).toString()));
	    nameValuePairs.add(new BasicNameValuePair("Birthday", birthday));

	    HttpPost httppost = new HttpPost("http://schonhercz.bl.ee/partyapp/user.php");  
	    try {
	    	
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
			if(!data.equals("OK")){
				throw new Exception();
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
	}

	@Override
	public User registerANewUser(String nick_name, String password, String email, int sex, String birthday) {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	    nameValuePairs.add(new BasicNameValuePair("action", "ADD"));
	    nameValuePairs.add(new BasicNameValuePair("NickName", nick_name));
	    nameValuePairs.add(new BasicNameValuePair("Password", password));
	    nameValuePairs.add(new BasicNameValuePair("Email", email));
	    nameValuePairs.add(new BasicNameValuePair("Sex", (new Integer(sex)).toString()));
	    nameValuePairs.add(new BasicNameValuePair("Birthday", birthday));

	    HttpPost httppost = new HttpPost("http://schonhercz.bl.ee/partyapp/user.php");  
	    try {
	    	
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
			if(data.equals("FAILED")){
				return null;
			}
			JSONObject jsonObject = new JSONObject(data);
        	int user_id = Integer.parseInt(jsonObject.getString("NewID"));
        	User newUser = new User(user_id, nick_name, password, email, sex, birthday, 0);
        	return newUser;
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
	    return null;
	}

	@Override
	public void setServices(int club_id, String[] services) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
		
		for(String actualService: services){
		
			nameValuePairs.add(new BasicNameValuePair("action", "ADD"));
	    	nameValuePairs.add(new BasicNameValuePair("ClubID", (new Integer(club_id)).toString()));
	    	nameValuePairs.add(new BasicNameValuePair("Type", actualService));
		
	    	HttpClient httpclient = new DefaultHttpClient();  
	    	HttpPost httppost = new HttpPost("http://schonhercz.bl.ee/partyapp/service.php");   

	    	try {
	    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
				HttpResponse response = httpclient.execute(httppost);
				String data = new BasicResponseHandler().handleResponse(response);
	        	try {
	            	if(data.equals("FAILED")){
	    				//itt baj lesz
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
	    nameValuePairs.add(new BasicNameValuePair("UserID", (new Integer(user_id)).toString()));
	    nameValuePairs.add(new BasicNameValuePair("ClubID", (new Integer(club_id)).toString()));
	    HttpClient httpclient = new DefaultHttpClient();  
    	HttpPost httppost = new HttpPost("http://schonhercz.bl.ee/partyapp/owner.php");   

    	try {
    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
        	try {
            	if(data.equals("FAILED")){
    				//itt baj lesz
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
	    nameValuePairs.add(new BasicNameValuePair("UserID", (new Integer(user_id)).toString()));
	    nameValuePairs.add(new BasicNameValuePair("ClubID", (new Integer(club_id)).toString()));
	  
	    HttpClient httpclient = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost("http://schonhercz.bl.ee/partyapp/favorite.php");
	    try {
    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
        	try {
            	if(data.equals("FAILED")){
    				//itt baj lesz
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
	    nameValuePairs.add(new BasicNameValuePair("FavID", (new Integer(fav_id)).toString()));
	  
	    HttpClient httpclient = new DefaultHttpClient();  
	    HttpPost httppost = new HttpPost("http://schonhercz.bl.ee/partyapp/favorite.php");
	    try {
	    	
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
			if(!data.equals("OK")){
				//bajvan
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

}
