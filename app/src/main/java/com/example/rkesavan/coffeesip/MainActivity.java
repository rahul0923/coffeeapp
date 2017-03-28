package com.example.rkesavan.coffeesip;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private EditText nameText;
    private CheckBox whipCreamChkBox;
    private CheckBox chocoChkBox;
    private Button addButton;
    private TextView quantity;
    private Button subtractButton;
    private Button orderButton;

    int quantityVal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
       this.nameText = (EditText) findViewById(R.id.name_field);
       this.whipCreamChkBox = (CheckBox) findViewById(R.id.whip_cream_select);
       this.chocoChkBox = (CheckBox) findViewById(R.id.choco_select);
       this.addButton = (Button) findViewById(R.id.add_button);
       this.quantity = (TextView) findViewById(R.id.quantity_val);
       this.quantityVal = Integer.parseInt(this.quantity.getText().toString());
       this.subtractButton = (Button) findViewById(R.id.subtract_button);
       this.orderButton = (Button) findViewById(R.id.order_button);
    }

    public void decrement(View view) {
        Log.d("MainActivity", "decrement quantity...");

        this.quantityVal--;
        this.updateQuantity();

    }

    public void increment(View view) {
        Log.d("MainActivity", "increment quantity...");
        this.quantityVal++;
        this.updateQuantity();
    }

    private void updateQuantity() {
        Log.d("MainActivity", "In updateQuantity " + this.quantity.getText().toString());
        this.quantity.setText(String.valueOf(this.quantityVal));
    }
    public void submitOrder(View view) {
        String orderSummary = this.getOrderSummary();
        Log.d("MainActivity", "submit order... " + orderSummary);
        this.composeEmail(orderSummary);
    }

    public void composeEmail(String orderSummary) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order Summary");
        emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }

    private String getOrderSummary() {
        String summaryText = "";
        summaryText += "Name: " + this.nameText.getText() + "\n" +
                       "Add whipped cream? " + this.whipCreamChkBox.isChecked() + "\n" +
                       "Add chocolate? " + this.chocoChkBox.isChecked() + "\n" +
                       "Quantity: " + this.quantity.getText() + "\n" +
                       "Total: " + this.getTotal() + "\n" +
                       "Thank You!";
        return summaryText;
    }

    private String getTotal() {
        int total = this.quantityVal * 5;
        return NumberFormat.getCurrencyInstance().format(total).toString();
    }
}
