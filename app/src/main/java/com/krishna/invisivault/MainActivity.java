package com.krishna.invisivault;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import android.util.Log;
public class MainActivity extends Activity {

	GridView gridGallery;
	Handler handler;
	GalleryAdapter adapter;

	ImageView imgSinglePick;
	//Button btnGalleryPick;
	Button btnGalleryPickMul;

	String action;
	ViewSwitcher viewSwitcher;
	ImageLoader imageLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		initImageLoader();
		init();


	}

	private void initImageLoader() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(defaultOptions).memoryCache(
				new WeakMemoryCache());

		ImageLoaderConfiguration config = builder.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);



	}

	@Override
	protected void onResume() {
		super.onResume();
		loadFromStorage();
	}

	private void init() {

		handler = new Handler();
		gridGallery = (GridView) findViewById(R.id.gridGallery);
		gridGallery.setFastScrollEnabled(true);
		adapter = new GalleryAdapter(getApplicationContext(), imageLoader);
		adapter.setMultiplePick(false);
		gridGallery.setAdapter(adapter);

		viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
		viewSwitcher.setDisplayedChild(1);

		imgSinglePick = (ImageView) findViewById(R.id.imgSinglePick);

/*		btnGalleryPick = (Button) findViewById(R.id.btnGalleryPick);
		btnGalleryPick.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(Action.ACTION_PICK);
				startActivityForResult(i, 100);

			}
		});*/

		btnGalleryPickMul = (Button) findViewById(R.id.btnGalleryPickMul);
		btnGalleryPickMul.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Action.ACTION_MULTIPLE_PICK);
				startActivityForResult(i, 200);
			}
		});

	}




	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
			adapter.clear();

			viewSwitcher.setDisplayedChild(1);
			String single_path = data.getStringExtra("single_path");
			imageLoader.displayImage("file://" + single_path, imgSinglePick);

		} else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
			String[] all_path = data.getStringArrayExtra("all_path");

			Log.e("size",""+all_path.length);


			try {

				for(int i=0;i<all_path.length;i++) {
					File image = new File(all_path[i]);
					BitmapFactory.Options bmOptions = new BitmapFactory.Options();
					Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);

					String ImageName = image.getName().toString();
					String subName =  ImageName.substring(ImageName.lastIndexOf("/") + 1, ImageName.length());

					String tempSubname = subName.substring(0,subName.lastIndexOf("."));
					//Log.e("Image name", tempSubname);
					saveToInternalSorage(bitmap,subName);
					//Log.e("orgin path", all_path[i]);
				}

			}catch (Exception e){
				Log.e("exc",e.toString());
			}

			/*for(int i=0;i<all_path.length;i++) {
				Log.e("path", all_path[i]);



			}*/

			loadFromStorage();

			/*
			ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

			for (String string : all_path) {

				CustomGallery item = new CustomGallery();
				item.sdcardPath = string;

				dataT.add(item);
			}

			viewSwitcher.setDisplayedChild(0);
			adapter.addAll(dataT);*/
		}
	}

	private void loadFromStorage(){




		String root = Environment.getExternalStorageDirectory().toString();

		String filepath = root+"/.KKK1/";

		File directory = new File(filepath);
		ArrayList<File> files = new ArrayList<File>();

		ArrayList<String> imagNames = new ArrayList<String>();


// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				files.add(file);
			}
		}

		for(int i=0;i<files.size();i++) {
	/*	Log.e("files ", files.get(i).getAbsolutePath());

			Log.e("files names", files.get(i).getName());
	*/		String tempName = files.get(i).getName().toString();
			/*String tempSubname = tempName.substring(0, tempName.lastIndexOf("."));
			tempSubname += ".jpg";*/
			imagNames.add(tempName);
		}


		ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

		for(int i=0;i<imagNames.size();i++){
			Log.e("files ", imagNames.get(i));
			CustomGallery item = new CustomGallery();
			item.sdcardPath = filepath+imagNames.get(i);

			Log.e("final path ",""+item.sdcardPath);
			dataT.add(item);
		}

		viewSwitcher.setDisplayedChild(0);
		adapter.addAll(dataT);

	}

	private void saveToInternalSorage(Bitmap bitmapImage,String imageName){

		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/.KKK1");
		myDir.mkdirs();
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);

		//String fname = "Image-"+ n +".kkk";

		//String fname = imageName+".kkk";
		String fname = imageName;

		File file = new File (myDir, fname);
		if (file.exists()) file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			bitmapImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			Log.e("path","done");

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("exc", e.toString());
		}

		/*ContextWrapper cw = new ContextWrapper(getApplicationContext());
		// path to /data/data/yourapp/app_data/imageDir
		File directory = cw.getDir("KKK", Context.MODE_PRIVATE);
		// Create imageDir
		File mypath=new File(directory,"kkk.jpg");

		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(mypath);

			// Use the compress method on the BitMap object to write image to the OutputStream
			bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

			fos.close();
		} catch (Exception e) {
			Log.e("exc", e.toString());
		}
		return directory.getAbsolutePath();*/
	}


	//end of class
}
