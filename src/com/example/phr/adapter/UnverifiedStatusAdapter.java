package com.example.phr.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.phr.NewStatusActivity;
import com.example.phr.R;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;
import com.example.phr.service.ActivityTrackerService;
import com.example.phr.service.FoodTrackerService;
import com.example.phr.service.VerificationService;
import com.example.phr.serviceimpl.ActivityTrackerServiceImpl;
import com.example.phr.serviceimpl.FoodTrackerServiceImpl;
import com.example.phr.serviceimpl.VerificationServiceImpl;
import com.example.phr.tools.DateTimeParser;

public class UnverifiedStatusAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<TrackerEntry> aListOfStatus;
	private final VerificationService verificationService;
	private final FoodTrackerService foodService;
	private final ActivityTrackerService activityService;

	private static class ViewHolder {
		TextView status;
		TextView date;
		TextView time;
		TextView word;
		TextView amount;
		TextView question;
		Button confirm;
		Button edit;
		Button ignore;
	}

	public UnverifiedStatusAdapter(Context aContext,
			List<TrackerEntry> aListOfStatus) {
		this.aListOfStatus = aListOfStatus;
		mContext = aContext;
		verificationService = new VerificationServiceImpl();
		foodService = new FoodTrackerServiceImpl();
		activityService = new ActivityTrackerServiceImpl();
	}

	@Override
	public int getCount() {
		return aListOfStatus.size();
	}

	@Override
	public Object getItem(int position) {
		return aListOfStatus.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_verification, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.verification_date);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.verification_time);
			viewHolder.status = (TextView) convertView
					.findViewById(R.id.verification_status);
			viewHolder.question = (TextView) convertView
					.findViewById(R.id.verification_question);
			viewHolder.word = (TextView) convertView
					.findViewById(R.id.verification_extracted_word);
			viewHolder.amount = (TextView) convertView
					.findViewById(R.id.verification_extracted_amount);
			viewHolder.confirm = (Button) convertView
					.findViewById(R.id.verification_confirm);
			viewHolder.edit = (Button) convertView
					.findViewById(R.id.verification_edit);
			viewHolder.ignore = (Button) convertView
					.findViewById(R.id.verification_ignore);

			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.date.setText(String.valueOf(DateTimeParser
				.getDate(aListOfStatus.get(position).getTimestamp())));
		viewHolder.time.setText(String.valueOf(DateTimeParser
				.getTime(aListOfStatus.get(position).getTimestamp())));
		viewHolder.status.setText(aListOfStatus.get(position).getStatus());

		viewHolder.amount.setVisibility(View.GONE);
		viewHolder.edit.setText("Edit");

		if (aListOfStatus.get(position).getClass()
				.equals(UnverifiedActivityEntry.class)) {
			viewHolder.question.setText("Did you perform: ");
			viewHolder.word.setText(((UnverifiedActivityEntry) aListOfStatus
					.get(position)).getActivityName());

			viewHolder.confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						UnverifiedActivityEntry unverified = (UnverifiedActivityEntry) aListOfStatus
								.get(position);

						Intent i = new Intent(mContext, NewStatusActivity.class);
						i.putExtra("unverified", TrackerInputType.ACTIVITY);
						i.putExtra("object", unverified);
						mContext.startActivity(i);
						/*
						 * ActivityTrackerEntry act = new ActivityTrackerEntry(
						 * unverified.getFacebookID(),
						 * unverified.getTimestamp(), unverified.getStatus(),
						 * unverified.getImage(), ActivitySingle activity,
						 * unverified.getCalorieBurnedPerHour() );
						 * 
						 * activityService.add(act);
						 */

						verificationService.delete(unverified);
						notifyDataSetChanged();
					} catch (EntryNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OutdatedAccessTokenException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			viewHolder.ignore.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					try {
						verificationService
								.delete((UnverifiedActivityEntry) aListOfStatus
										.get(position));
						aListOfStatus.remove(position);
						notifyDataSetChanged();
					} catch (EntryNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OutdatedAccessTokenException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		}

		else if (aListOfStatus.get(position).getClass()
				.equals(UnverifiedFoodEntry.class)) {
			viewHolder.question.setText("Did you eat: ");
			viewHolder.word.setText(((UnverifiedFoodEntry) aListOfStatus
					.get(position)).getFoodName());
			viewHolder.amount.setVisibility(View.VISIBLE);
			viewHolder.amount.setText(((UnverifiedFoodEntry) aListOfStatus
					.get(position)).getServingSize() + "");

			viewHolder.confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					UnverifiedFoodEntry unverified = (UnverifiedFoodEntry) aListOfStatus
							.get(position);

					Intent i = new Intent(mContext, NewStatusActivity.class);
					i.putExtra("unverified", TrackerInputType.FOOD);
					i.putExtra("object", unverified);
					mContext.startActivity(i);
					/*
					 * FoodTrackerEntry food = new FoodTrackerEntry(
					 * unverified.getFacebookID(), unverified.getTimestamp(),
					 * unverified.getStatus(), unverified.getImage(),
					 * unverified.getFood(), unverified.getServingSize());
					 * 
					 * foodService.add(food);
					 * 
					 * verificationService.delete(unverified);
					 */

					notifyDataSetChanged();
				}
			});

			viewHolder.ignore.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					try {
						verificationService
								.delete((UnverifiedFoodEntry) aListOfStatus
										.get(position));
						aListOfStatus.remove(position);
						notifyDataSetChanged();
					} catch (EntryNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OutdatedAccessTokenException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		}

		else if (aListOfStatus.get(position).getClass()
				.equals(UnverifiedRestaurantEntry.class)) {
			viewHolder.question.setText("Did you eat at: ");
			viewHolder.word.setText(((UnverifiedRestaurantEntry) aListOfStatus
					.get(position)).getRestaurantName());
			viewHolder.edit.setText("Edit");

			viewHolder.confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						UnverifiedRestaurantEntry unverified = (UnverifiedRestaurantEntry) aListOfStatus
								.remove(position);

						/*
						 * FoodTrackerEntry food = new FoodTrackerEntry(
						 * unverified.getFacebookID(),
						 * unverified.getTimestamp(), unverified.getStatus(),
						 * unverified.getImage(), unverified.getFood,
						 * unverified.getServingSize());
						 * 
						 * foodService.add(food);
						 */

						verificationService.delete(unverified);
						notifyDataSetChanged();
					} catch (EntryNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OutdatedAccessTokenException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			viewHolder.ignore.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					try {
						verificationService
								.delete((UnverifiedRestaurantEntry) aListOfStatus
										.get(position));
						aListOfStatus.remove(position);
						notifyDataSetChanged();
					} catch (EntryNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OutdatedAccessTokenException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		}

		else if (aListOfStatus.get(position).getClass()
				.equals(UnverifiedSportsEstablishmentEntry.class)) {
			viewHolder.question.setText("Did go eat at: ");
			// viewHolder.word.setText(((UnverifiedSportsEstablishmentEntry)aListOfStatus.get(position)).getEstablishment());
		}

		return convertView;
	}
}