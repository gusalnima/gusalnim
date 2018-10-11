package kr.co.gusalnim.template.util;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.scglab.common.listadapter.FlexAdapter;
import com.scglab.common.listadapter.ItemRenderer;
import com.scglab.common.listadapter.OnItemClickEventHandler;
import com.scglab.common.listadapter.RendererFactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.gusalnim.template.R;

public class MessageHelper {

    //date
    private static DatePickerDialog datePickerDialog;

    public static void datePickerShow(Context context, int year, int month, int day, DatePickerDialog.OnDateSetListener onDateSetListener) {
        datePickerClose();

        datePickerDialog = new DatePickerDialog(
                context,
                getTheme(),
                onDateSetListener,
                year,
                month,
                day);

        try {
            datePickerDialog.show();
        } catch (Exception ignored) {
        }
    }

    public static void datePickerClose() {
        if (null != datePickerDialog && datePickerDialog.isShowing()) {
            datePickerDialog.dismiss();
            datePickerDialog = null;
        }
    }

    //time
    private static TimePickerDialog timePickerDialog;

    public static void timePickerShow(Context context, int hourOfDay, int min, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        datePickerClose();

        timePickerDialog = new TimePickerDialog(
                context,
                getTheme(),
                onTimeSetListener,
                hourOfDay,
                min,
                false);

        try {
            timePickerDialog.show();
        } catch (Exception ignored) {
        }
    }

    public static void timePickerClose() {
        if (null != timePickerDialog && timePickerDialog.isShowing()) {
            timePickerDialog.dismiss();
            timePickerDialog = null;
        }
    }

    //alert
    public static void alert(Context context, int msgResource, Object... args) {
        alert(context, context.getString(msgResource), args);
    }

    public static void alert(Context context, String msg, Object... args) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_Dialog);
        builder.setTitle(R.string.app_name);
        builder.setMessage(msg);

        if (null != args) {
            builder.setCancelable(args.length <= 1);

            if (args.length > 0) {
                if (args[0] instanceof String) {
                    builder.setPositiveButton((String) args[0], args.length > 1 ? (DialogInterface.OnClickListener) args[1] : null);
                } else if (args[0] instanceof Integer) {
                    builder.setPositiveButton((Integer) args[0], args.length > 1 ? (DialogInterface.OnClickListener) args[1] : null);
                }
            }

            if (args.length > 2) {
                if (args[2] instanceof String) {
                    builder.setNegativeButton((String) args[2], args.length > 3 ? (DialogInterface.OnClickListener) args[3] : null);
                } else if (args[2] instanceof Integer) {
                    builder.setNegativeButton((Integer) args[2], args.length > 3 ? (DialogInterface.OnClickListener) args[3] : null);
                }
            }

            if (args.length > 4) {
                if (args[4] instanceof String) {
                    builder.setNeutralButton((String) args[4], args.length > 5 ? (DialogInterface.OnClickListener) args[5] : null);
                } else if (args[4] instanceof Integer) {
                    builder.setNeutralButton((Integer) args[4], args.length > 5 ? (DialogInterface.OnClickListener) args[5] : null);
                }
            }
        }


        try {
            builder.show();
        } catch (Exception ignored) {
        }
    }

    //bottom sheet image dialog
    public static void bottomImageAlert(final Context context, final String title, final List<Integer> image, final String[] list, final IBottomSheetItemClick iBottomSheetItemClick) {
        ArrayList<String> stringList = new ArrayList<>();
        for (String string : list) {
            stringList.add(string);
        }
        bottomImageAlert(context, title, image, stringList, iBottomSheetItemClick);
    }

    public static void bottomImageAlert(final Context context, final String title, final List<Integer> image, final List<String> list, final IBottomSheetItemClick iBottomSheetItemClick) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alert_bottom_sheet, null);
        ((TextView) view.findViewById(R.id.txtTitle)).setText(title);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        RendererFactory rendererFactory = new RendererFactory();
        rendererFactory.put(BottomSheetItemViewHolder.class, R.layout.adapter_bottom_sheet_item);

        ArrayList<Object> bottomSheetItems = new ArrayList<>();
        int index = 0;
        for (String string : list) {
            index++;
            bottomSheetItems.add(new BottomSheetItem(index, string, image.get(index - 1)));
        }
        final FlexAdapter flexAdapter = new FlexAdapter(rendererFactory);
        flexAdapter.setModels(bottomSheetItems);
        flexAdapter.setOnItemClickEventHandler(new OnItemClickEventHandler() {
            @Override
            public void onItemClick(Object o) {
                iBottomSheetItemClick.onClick(((BottomSheetItem) o).getIndex());
                bottomSheetDialog.dismiss();
            }

            @Override
            public void onItemLongClick(Object o) {
                bottomSheetDialog.dismiss();
            }

            @Override
            public void onChildViewClick(Object o, int i) {

            }

            @Override
            public void onChildViewLongClick(Object o, int i) {

            }
        });
        recyclerView.setAdapter(flexAdapter);

        try {
            bottomSheetDialog.show();
        } catch (Exception ignored) {
        }
    }

    //bottom sheet dialog
    public static void bottomAlert(final Context context, final String title, final String[] list, final IBottomSheetItemClick iBottomSheetItemClick) {
        ArrayList<String> stringList = new ArrayList<>();
        for (String string : list) {
            stringList.add(string);
        }
        bottomAlert(context, title, stringList, iBottomSheetItemClick);
    }

    public static void bottomAlert(final Context context, final String title, final List<String> list, final IBottomSheetItemClick iBottomSheetItemClick) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alert_bottom_sheet, null);
        ((TextView) view.findViewById(R.id.txtTitle)).setText(title);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        RendererFactory rendererFactory = new RendererFactory();
        rendererFactory.put(BottomSheetItemViewHolder.class, R.layout.adapter_bottom_sheet_item);

        ArrayList<Object> bottomSheetItems = new ArrayList<>();
        int index = 0;
        for (String string : list) {
            bottomSheetItems.add(new BottomSheetItem(index++, string));
        }
        final FlexAdapter flexAdapter = new FlexAdapter(rendererFactory);
        flexAdapter.setModels(bottomSheetItems);
        flexAdapter.setOnItemClickEventHandler(new OnItemClickEventHandler() {
            @Override
            public void onItemClick(Object o) {
                iBottomSheetItemClick.onClick(((BottomSheetItem) o).getIndex());
                bottomSheetDialog.dismiss();
            }

            @Override
            public void onItemLongClick(Object o) {
                bottomSheetDialog.dismiss();
            }

            @Override
            public void onChildViewClick(Object o, int i) {

            }

            @Override
            public void onChildViewLongClick(Object o, int i) {

            }
        });
        recyclerView.setAdapter(flexAdapter);

        try {
            bottomSheetDialog.show();
        } catch (Exception ignored) {
        }
    }

    public interface IBottomSheetItemClick {
        void onClick(int position);
    }


    @FlexAdapter.Item
    public static class BottomSheetItem {
        private int index;
        private String data;
        private int image;

        public BottomSheetItem(int index, String data) {
            this.index = index;
            this.data = data;
        }

        public BottomSheetItem(int index, String data, int image) {
            this.index = index;
            this.data = data;
            this.image = image;
        }

        public int getIndex() {
            return index;
        }

        public String getData() {
            return data;
        }

        public int getImage() {
            return image;
        }
    }



    public static class BottomSheetItemViewHolder extends ItemRenderer<BottomSheetItem> {
        private TextView txtLabel;

        public BottomSheetItemViewHolder(View view) {
            super(view);
        }

        @Override
        protected void onBind(final BottomSheetItem model) {
            Log.i("gusalnim","model.getData() : " + model.getData());
            Log.i("gusalnim"," / txtLabel : " + txtLabel);
            txtLabel.setText(model.getData());
        }


    }

    public static class BottomSheetImageItemViewHolder extends ItemRenderer<BottomSheetItem> {

        private TextView txtLabel;
        private Context context;

        public BottomSheetImageItemViewHolder(View view) {
            super(view);
        }

        @Override
        protected void onBind(final BottomSheetItem model) {
            txtLabel.setText(model.getData());
            Drawable icon = ContextCompat.getDrawable(context,model.getImage());
            txtLabel.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            txtLabel.setCompoundDrawablePadding(DisplayUtil.PixelFromDP(context,33));
        }
    }


    //toast
    private static WeakReference<Toast> toastWeakReference;

    public static void toast(Context context, int msgResource) {
        toast(context, context.getString(msgResource));
    }

    public static void toast(Context context, String msg) {

		/*Toast toast;

		if (null != toastWeakReference) {
			toast = toastWeakReference.get();
			if (null != toast) {
				toast.cancel();
			}
		}

		toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		//toast.setView(tvToastMsg);

		try {
			toast.show();
		} catch (Exception ignored) {
		}

		toastWeakReference = new WeakReference<>(toast);*/



        Toast toast;

        if (null != toastWeakReference) {
            toast = toastWeakReference.get();
            if (null != toast) {
                toast.cancel();
            }
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        //((TextView) layout.findViewById(R.id.txtInfo)).setText(speak.getSpeakerInfo().getName());
        TextView txtMessage = ((TextView) layout.findViewById(R.id.txtMessage));
        txtMessage.setText(msg);
        if(txtMessage.getLineCount() < 2){
            txtMessage.setGravity(View.TEXT_ALIGNMENT_CENTER);
        }
        //((CircleImageView) layout.findViewById(R.id.imgThumb)).setImageUrl(ThumbnailHelper.centerInside(100, 100, speak.getSpeakerInfo().getProfile()));

        toast = new Toast(context);
        //toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, toast.getXOffset(), yOffSet);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        try {
            toast.show();
        } catch (Exception ignored) {
        }

        toastWeakReference = new WeakReference<>(toast);
    }

    //speak
	/*
	private static WeakReference<Toast> speakToastWeakReference;

	public static void speakToast(Activity activity, Speak speak, int yOffSet) {
		Toast toast;

		if (null != speakToastWeakReference) {
			toast = speakToastWeakReference.get();
			if (null != toast) {
				toast.cancel();
			}
		}

		LayoutInflater inflater = activity.getLayoutInflater();
		View layout = inflater.inflate(R.layout.custom_speak_toast, null);

		((TextView) layout.findViewById(R.id.txtInfo)).setText(speak.getSpeakerInfo().getName());
		((TextView) layout.findViewById(R.id.txtMessage)).setText(speak.getMsg());
		((CircleImageView) layout.findViewById(R.id.imgThumb)).setImageUrl(ThumbnailHelper.centerInside(100, 100, speak.getSpeakerInfo().getProfile()));

		toast = new Toast(activity);
		toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, toast.getXOffset(), yOffSet);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		try {
			toast.show();
		} catch (Exception ignored) {
		}

		speakToastWeakReference = new WeakReference<>(toast);
	}
	*/

    //progress
    private static WeakReference<CustomProgressDialog> progressDialogWeakReference;

    public static void showProgress(Context context) {
        showProgress(context, null);
    }

    public static void showProgress(Context context, DialogInterface.OnCancelListener onCancelListener) {
        if (null != progressDialogWeakReference) {
            closeProgress();
        }

        CustomProgressDialog progressDialog = new CustomProgressDialog(context);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //ProgressDialog progressDialog = new ProgressDialog(context, getTheme());
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.setMessage(context.getString(R.string.common_waiting));
        if (null != onCancelListener) progressDialog.setOnCancelListener(onCancelListener);
        else progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        try {
            progressDialog.show();
        } catch (Exception ignored) {
        }

        progressDialogWeakReference = new WeakReference<>(progressDialog);
    }


    //progress
    private static WeakReference<ProgressDialog> progressDialogForUploadWeakReference;

    public static void showProgressForUpload(final Context context, final DialogInterface.OnCancelListener onCancelListener) {
        closeProgress();

        //ProgressDialog progressDialog = new CustomProgressDialog(context);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onCancelListener.onCancel(dialog);
            }
        });
        progressDialog.setMessage(context.getString(R.string.common_waiting));
        if (null != onCancelListener) progressDialog.setOnCancelListener(onCancelListener);
        progressDialog.setCancelable(false);

        try {
            progressDialog.show();
        } catch (Exception ignored) {
        }

        progressDialogForUploadWeakReference = new WeakReference<>(progressDialog);
    }

    public static void uploadProgressDialogUpdate(int percentage) {
        if (null != progressDialogForUploadWeakReference) {
            ProgressDialog progressDialog = progressDialogForUploadWeakReference.get();
            progressDialog.setProgress(percentage);
        }
    }

    public static void closeProgress() {
        if (null != progressDialogWeakReference) {
            CustomProgressDialog progressDialog = progressDialogWeakReference.get();

            if (null != progressDialog && progressDialog.isShowing()) {
                DialogDismisser.dismiss(progressDialog);
            }

            progressDialogWeakReference.clear();
            progressDialogWeakReference = null;
        }
        if (null != progressDialogForUploadWeakReference) {
            ProgressDialog progressDialog = progressDialogForUploadWeakReference.get();
            if (null != progressDialog) {
                if (progressDialog.isShowing()) progressDialog.dismiss();
            }
            progressDialogForUploadWeakReference.clear();
            progressDialogForUploadWeakReference = null;
        }
    }

    public static boolean hasProgress() {
        return (null != progressDialogWeakReference && null != progressDialogWeakReference.get() && progressDialogWeakReference.get().isShowing()) ||
                (null != progressDialogForUploadWeakReference && null != progressDialogForUploadWeakReference.get() && progressDialogForUploadWeakReference.get().isShowing());
    }

    private static int getTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) return R.style.AppTheme_Dialog;
        return R.style.AppTheme_Dialog_Transparent;
    }


    //이메일 유효성 검사
    public static boolean checkEmail(String email) {

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isNormal = m.matches();
        return isNormal;

    }


}
