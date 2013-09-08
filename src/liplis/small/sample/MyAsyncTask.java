package liplis.small.sample;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;

public class MyAsyncTask
  extends AsyncTask<String, Integer, Long>
  implements OnCancelListener{

  final String TAG = "MyAsyncTask";
  ProgressDialog dialog;
  Context context;

  public MyAsyncTask(Context context){
    this.context = context;
  }

  @Override
  protected void onPreExecute() {
    Log.d(TAG, "onPreExecute");
    dialog = new ProgressDialog(context);
    dialog.setTitle("Please wait");
    dialog.setMessage("Loading data...");
    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    dialog.setCancelable(true);
    dialog.setOnCancelListener(this);
    dialog.setMax(100);
    dialog.setProgress(0);
    dialog.show();
  }

  @Override
  protected Long doInBackground(String... params) {
    Log.d(TAG, "doInBackground - " + params[0]);

    try {
      for(int i=0; i<10; i++){
        if(isCancelled()){
          Log.d(TAG, "Cancelled!");
          break;
        }
        Thread.sleep(1000);
        publishProgress((i+1) * 10);
      }
    } catch (InterruptedException e) {
      Log.d(TAG, "InterruptedException in doInBackground");
    }
    return 123L;
  }

  @Override
  protected void onProgressUpdate(Integer... values) {
    Log.d(TAG, "onProgressUpdate - " + values[0]);
    dialog.setProgress(values[0]);
  }

  @Override
  protected void onCancelled() {
    Log.d(TAG, "onCancelled");
    dialog.dismiss();
  }

  @Override
  protected void onPostExecute(Long result) {
    Log.d(TAG, "onPostExecute - " + result);
    dialog.dismiss();
  }

  @Override
  public void onCancel(DialogInterface dialog) {
    Log.d(TAG, "Dialog onCancell... calling cancel(true)");
    this.cancel(true);
  }
}