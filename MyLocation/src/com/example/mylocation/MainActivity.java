package com.example.mylocation;


import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity
{

    private TextView positonTextView;
    private String provider;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        positonTextView = (TextView) findViewById(R.id.postion_textView);
        //获取LocationManager的一个实例，这里需要注意的是他的实例只能通过下面这种方式来获取，直接实例化LocationManager是不被允许的
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        
        // 如果不确定哪些位置提供器可用，可以用下面方法判断 。获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.NETWORK_PROVIDER))
        {
            provider = LocationManager.NETWORK_PROVIDER;
        }
           else   if (providerList.contains(LocationManager.GPS_PROVIDER))
        {
            provider = LocationManager.GPS_PROVIDER;
        }
           else
        {
            // 当没有可用的位置提供器时，弹出Toash提示用户
            Toast.makeText(this, "No Location provider to use", Toast.LENGTH_SHORT).show();
            return;
        }

        //通过provider获得Location的对象
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null)
        {
            // 显示当前设备的位置信息
            showLocation(location);
            
        }
        
//        得到了LocationManager的实例locatonManager以后，我们通过下面的语句来注册一个周期性的位置更新
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
        
        /*第一个参数 是位置提供器的类型，上面的代码可以获得
         * 第二个参数是监听位置变化的时间，单位毫秒
         * 第三个参数是监听位置变化的距离间隔，单位米
         * 第四个三个是个监听器，需要 实例化 LocationListener
         */
    }

    LocationListener locationListener = new LocationListener()
    {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            // Provider的转态在可用、暂时不可用和无服务三个状态直接切换时触发此函数    
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            // Provider被disable时触发此函数，比如GPS被关闭
        }

        @Override
        public void onProviderDisabled(String provider)
        {
        //  Provider被enable时触发此函数，比如GPS被打开
        }

        @Override
        public void onLocationChanged(Location location)
        {
            //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发 

            // 更新当前设备的位置信息
            showLocation(location);
        }
    };
    protected void onDestroy()
    {
        super.onDestroy();
        if (locationManager != null)
        {
            // 关闭程序时将监听器移除
            locationManager.removeUpdates(locationListener);
        }
    }
    private void showLocation(Location location)
    {
        String currentPosition = "latitude is" + location.getLatitude() + "\n" + "longitude is "
                + location.getLongitude()+"\n address=";
        
          try {
          List<Address> list=  new Geocoder(this).getFromLocation( location.getLatitude(), location.getLongitude(), 1);
          currentPosition+=list.get(0);
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        System.out.println(currentPosition);
        positonTextView.setText(currentPosition);
    }

}