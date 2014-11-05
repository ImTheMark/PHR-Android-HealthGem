package com.example.phr.adapter;

import java.io.FileNotFoundException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phr.NewStatusActivity;
import com.example.phr.R;
import com.example.phr.VerificationListPickerActivity;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.ImageHandlerException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.mobile.models.PHRImageType;
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
import com.example.phr.tools.ImageHandler;

public class UnverifiedStatusAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<TrackerEntry> aListOfStatus;
	private final VerificationService verificationService;
	private final FoodTrackerService foodService;
	private final ActivityTrackerService activityService;
	private VerificationService verificationService;

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
		ImageView statusImg;
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
			viewHolder.statusImg = (ImageView) convertView
					.findViewById(R.id.verification_statusPhotoHolder);

			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.date.setText(String.valueOf(DateTimeParser
				.getDate(aListOfStatus.get(position).getTimestamp())));
		viewHolder.time.setText(String.valueOf(DateTimeParser
				.getTime(aListOfStatus.get(position).getTimestamp())));
		viewHolder.status.setText(aListOfStatus.get(position).getStatus());

		if (aListOfStatus.get(position).getImage() != null) {

			try {
				viewHolder.statusImg.setImageDrawable(new BitmapDrawable(mContext.getResources(), 
						ImageHandler.decodeImage(aListOfStatus.get(position).getImage().getEncodedImage())));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ImageHandlerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			viewHolder.statusImg.setVisibility(View.VISIBLE);

		} else {
			viewHolder.statusImg.setVisibility(View.GONE);
		}

		viewHolder.amount.setVisibility(View.GONE);
		viewHolder.edit.setText("Edit");

		if (aListOfStatus.get(position).getClass()
				.equals(UnverifiedActivityEntry.class)) {
			viewHolder.question.setText("Did you perform: ");
			viewHolder.word.setText(((UnverifiedActivityEntry) aListOfStatus
					.get(position)).getActivity().getName());

			viewHolder.confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {


					UnverifiedActivityEntry unverified = (UnverifiedActivityEntry) aListOfStatus
							.get(position);
					if (aListOfStatus.get(position).getImage() != null){

						try {
							verificationService.storeEncodedImage(aListOfStatus.get(position).getImage().getEncodedImage());
							unverified.setImage(new PHRImage(verificationService.getImageFileName(), PHRImageType.FILENAME));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ImageHandlerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					Intent i = new Intent(mContext, NewStatusActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("unverified", TrackerInputType.ACTIVITY);
					i.putExtra("object", unverified);
					mContext.startActivity(i);
					/*
					 * ActivityTrackerEntry act = new ActivityTrackerEntry(
					 * unverified.getFacebookID(), unverified.getTimestamp(),
					 * unverified.getStatus(), unverified.getImage(),
					 * ActivitySingle activity,
					 * unverified.getCalorieBurnedPerHour() );
					 * 
					 * activityService.add(act);
					 * 
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
					.get(position)).getFood().getName());
			viewHolder.amount.setVisibility(View.VISIBLE);
			viewHolder.amount.setText(((UnverifiedFoodEntry) aListOfStatus
					.get(position)).getServingCount() + "");

			viewHolder.confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					UnverifiedFoodEntry unverified = (UnverifiedFoodEntry) aListOfStatus
							.get(position);
					if (aListOfStatus.get(position).getImage() != null){

						try {
							verificationService.storeEncodedImage(aListOfStatus.get(position).getImage().getEncodedImage());
							unverified.setImage(new PHRImage(verificationService.getImageFileName(), PHRImageType.FILENAME));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ImageHandlerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					Intent i = new Intent(mContext, NewStatusActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
					.get(position)).getRestaurant().getName());
			viewHolder.edit.setText("Edit");

			viewHolder.confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					UnverifiedRestaurantEntry unverified = (UnverifiedRestaurantEntry) aListOfStatus
							.get(position);

					if (aListOfStatus.get(position).getImage() != null){

						try {
							verificationService.storeEncodedImage(aListOfStatus.get(position).getImage().getEncodedImage());
							unverified.setImage(new PHRImage(verificationService.getImageFileName(), PHRImageType.FILENAME));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ImageHandlerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					verificationService
							.addFoodListToTemporaryDatabase(unverified
									.getFoods());
					unverified.setFoods(null);

					Intent i = new Intent(mContext,
							VerificationListPickerActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("restaurant", unverified);
					mContext.startActivity(i);
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
			viewHolder.question.setText("Did go at: ");
			viewHolder.word
					.setText(((UnverifiedSportsEstablishmentEntry) aListOfStatus
							.get(position)).getSportEstablishment().getName());
			viewHolder.confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					UnverifiedSportsEstablishmentEntry unverified = (UnverifiedSportsEstablishmentEntry) aListOfStatus
							.get(position);
					
					if (aListOfStatus.get(position).getImage() != null){

						try {
							verificationService.storeEncodedImage(aListOfStatus.get(position).getImage().getEncodedImage());
							unverified.setImage(new PHRImage(verificationService.getImageFileName(), PHRImageType.FILENAME));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ImageHandlerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					verificationService
							.addActivityListToTemporaryDatabase(unverified
									.getActivities());
					unverified.setActivities(null);

					Intent i = new Intent(mContext,
							VerificationListPickerActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("sportestablishment", unverified);
					mContext.startActivity(i);
				}
			});

			viewHolder.ignore.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					try {
						verificationService
								.delete((UnverifiedSportsEstablishmentEntry) aListOfStatus
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

		return convertView;
	}
}