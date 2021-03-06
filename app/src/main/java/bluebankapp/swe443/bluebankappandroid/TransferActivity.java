package bluebankapp.swe443.bluebankappandroid;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class TransferActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    EditText editAmount;
    EditText personNameEdit;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Transfer");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        //personNameEdit.setText(getIntent().getExtras().getString("user"));

    }

    public void doTransactionClick(View v){

        editAmount=(EditText) findViewById(R.id.editAmount);
        personNameEdit=(EditText) findViewById(R.id.personNameEdit);
        if(validateInput()){
            Double amountDb= Double.parseDouble(editAmount.getText().toString());
            String u = getSharedPreferences("bluebank", MODE_PRIVATE).getString("username", "");
            String p = getSharedPreferences("bluebank", MODE_PRIVATE).getString("password", "");
            String ip = getSharedPreferences("bluebank", MODE_PRIVATE).getString("ip", "");

            // Send LOGIN request to server.
            StringBuilder req = new StringBuilder();

            // Create the request string
            // op code | username | password | amount
            // 0  #1  #2       #3  #4
            // w/d#jlm#letmein0#abc#50.00
            req.append("t" + ClientLogic.DELIM); // OP CODE
            req.append(u + ClientLogic.DELIM); // USERNAME
            req.append(p + ClientLogic.DELIM); // PASSWORD
            req.append(personNameEdit.getText().toString() + ClientLogic.DELIM); // DESTINATION
            req.append(Double.toString(amountDb)); // AMOUNT

            // Send the request string and get the response.
            new ClientLogic.ServerRequest().execute(this, req.toString(), ip);
        }

    }
//
    public void doReadClick(View v){

        // Biometrics on all qualifying devices.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1337);

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                return;
            }
        }

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera

    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        builder.setPositiveButton("Confirm Transfer",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                        setContentView(R.layout.activity_transfer);
                        EditText u = (EditText) findViewById(R.id.personNameEdit);
                        u.setText(rawResult.getText());
                        //Intent intent = new Intent(getBaseContext(), TransferActivity.class);
                        //intent.putExtra("user",rawResult.getText().toString());
                        //startActivity(intent);
                        //startActivity(new Intent(getBaseContext(), BankMainActivity.class));
                        //finish();
                    }
                });
        AlertDialog alert1 = builder.create();
        alert1.show();

        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }

    public boolean validateInput(){
        boolean check=true;
        if(personNameEdit.getText().toString().isEmpty()){
            personNameEdit.setError("Insert Username to transfer" );
            check= false;
        }
        if(TextUtils.isEmpty(editAmount.getText().toString())){
            editAmount.setError("Insert Amount");
            check= false;
        }

        return check;
    }

}
