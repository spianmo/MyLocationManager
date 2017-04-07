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
        //��ȡLocationManager��һ��ʵ����������Ҫע���������ʵ��ֻ��ͨ���������ַ�ʽ����ȡ��ֱ��ʵ����LocationManager�ǲ��������
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        
        // �����ȷ����Щλ���ṩ�����ã����������淽���ж� ����ȡ���п��õ�λ���ṩ��
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
            // ��û�п��õ�λ���ṩ��ʱ������Toash��ʾ�û�
            Toast.makeText(this, "No Location provider to use", Toast.LENGTH_SHORT).show();
            return;
        }

        //ͨ��provider���Location�Ķ���
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null)
        {
            // ��ʾ��ǰ�豸��λ����Ϣ
            showLocation(location);
            
        }
        
//        �õ���LocationManager��ʵ��locatonManager�Ժ�����ͨ������������ע��һ�������Ե�λ�ø���
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
        
        /*��һ������ ��λ���ṩ�������ͣ�����Ĵ�����Ի��
         * �ڶ��������Ǽ���λ�ñ仯��ʱ�䣬��λ����
         * �����������Ǽ���λ�ñ仯�ľ���������λ��
         * ���ĸ������Ǹ�����������Ҫ ʵ���� LocationListener
         */
    }

    LocationListener locationListener = new LocationListener()
    {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            // Provider��ת̬�ڿ��á���ʱ�����ú��޷�������״ֱ̬���л�ʱ�����˺���    
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            // Provider��disableʱ�����˺���������GPS���ر�
        }

        @Override
        public void onProviderDisabled(String provider)
        {
        //  Provider��enableʱ�����˺���������GPS����
        }

        @Override
        public void onLocationChanged(Location location)
        {
            //������ı�ʱ�����˺��������Provider������ͬ�����꣬���Ͳ��ᱻ���� 

            // ���µ�ǰ�豸��λ����Ϣ
            showLocation(location);
        }
    };
    protected void onDestroy()
    {
        super.onDestroy();
        if (locationManager != null)
        {
            // �رճ���ʱ���������Ƴ�
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