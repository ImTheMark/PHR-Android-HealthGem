package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import com.example.phr.adapter.UnverifiedStatusAdapter;
import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.service.VerificationService;
import com.example.phr.serviceimpl.VerificationServiceImpl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ListView;

public class VerificationActivityTest extends Activity {
	
	SwipeRefreshLayout swipeLayout;
	ListView listView;
	VerificationService vService;
	UnverifiedStatusAdapter adapter;
	List<TrackerEntry> unverifiedList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
	    swipeLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new GetPostsFromServer().execute();
			}
		});
	    
	    listView = (ListView) findViewById(R.id.verification_listview_test);
		vService = new VerificationServiceImpl();
		try {
			unverifiedList = vService.getAll();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    adapter = new UnverifiedStatusAdapter(HealthGem.getContext(), unverifiedList);
	}

	class GetPostsFromServer extends AsyncTask<Void, Void, Void> {
	
	    private List<TrackerEntry> list;
	    private final ProgressDialog dialog = new ProgressDialog(HealthGem.getContext());
	    
	    @Override
	    protected void onPreExecute() {
	    	super.onPreExecute();
	    	list = new ArrayList<TrackerEntry>();
	    	dialog.setMessage("Downloading contacts...");
	        dialog.show();   
	    }    
	
	    @Override
	    protected Void doInBackground(Void... params) {
	    	try {
				vService.updateListOfUnverifiedPosts();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OutdatedAccessTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return null;
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	    	
	    	super.onPostExecute(result);
	        dialog.dismiss();
	        
	        try {
				list = vService.getAll();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OutdatedAccessTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        if(list != null && !list.isEmpty()){
	        	unverifiedList = list;
	        	adapter.notifyDataSetChanged();
	        }
	        
	        swipeLayout.setRefreshing(false);
	
	    }
	}

}


