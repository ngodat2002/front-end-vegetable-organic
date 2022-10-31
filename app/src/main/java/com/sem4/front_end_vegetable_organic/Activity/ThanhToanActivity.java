package com.sem4.front_end_vegetable_organic.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import com.sem4.front_end_vegetable_organic.R;
import com.sem4.front_end_vegetable_organic.api.ApiClient;
import com.sem4.front_end_vegetable_organic.api.InterfaceApi;
import com.sem4.front_end_vegetable_organic.model.EventBus.TinhTongEvent;
import com.sem4.front_end_vegetable_organic.model.User;
import com.sem4.front_end_vegetable_organic.model.userCheckout;
import com.sem4.front_end_vegetable_organic.utils.Utils;
import org.greenrobot.eventbus.EventBus;
import java.text.DecimalFormat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien,txtsodt,txtemail;
    EditText editdiachi, editsdt;
    AppCompatButton btndathang;
    ImageView imagehome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        initView();
        initControl();
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        long tongtien=getIntent().getLongExtra("tongtien",0);
        //secsion
        User user = new User();
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user",user.getEmail());
        txttongtien.setText(decimalFormat.format(tongtien)+" VNĐ");
        txtemail.setText(email);
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_diachi=editdiachi.getText().toString().trim();
                String sdt = editsdt.getText().toString().trim();
//                String reg = "^(0)(\\s)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
//                String regex ="/^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/";

                if(TextUtils.isEmpty(sdt)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }
                if(editsdt.getText().toString().trim().length()<10){
                    Toast.makeText(getApplicationContext(), "Nhập sai định dạng, vui lòng nhập số chính xác", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }else{
                    userCheckout userCheckout1 = new userCheckout();
                    userCheckout1.setAddress(str_diachi);
                    userCheckout1.setPhone(sdt);
                    addCart(userCheckout1,email);
                }
            }
        });
    }

    private void initView() {
        toolbar=findViewById(R.id.toolbar);
        txttongtien=findViewById(R.id.txttongtien);
        txtemail=findViewById(R.id.txtemail);

        editdiachi=findViewById(R.id.editdiachi);
        editsdt=findViewById(R.id.editsdt);
        btndathang=findViewById(R.id.btndathang);

    }
    public void addCart(userCheckout userCheckout1, String email){

        InterfaceApi interfaceApi = ApiClient.getClient().create(InterfaceApi.class);

        Call<userCheckout> addProduct = interfaceApi.checkout(userCheckout1,email);
        addProduct.enqueue(new Callback<userCheckout>() {
            @Override
            public void onResponse(Call<userCheckout> call, Response<userCheckout> response) {
                if(response.isSuccessful()){

                    Toast.makeText(ThanhToanActivity.this,"Chúc mừng bạn đặt hàng thành công^^",Toast.LENGTH_LONG).show();
                    Utils.manggiohang.clear();

                    EventBus.getDefault().postSticky(new TinhTongEvent());
                    startActivity(new Intent(ThanhToanActivity.this,HomeActivity.class));

                }
            }

            @Override
            public void onFailure(Call<userCheckout> call, Throwable t) {

                String message = "Invalid";
                Toast.makeText(ThanhToanActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}