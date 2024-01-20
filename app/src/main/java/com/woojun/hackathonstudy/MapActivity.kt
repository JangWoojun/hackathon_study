package com.woojun.hackathonstudy

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.woojun.hackathonstudy.databinding.ActivityMapBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            mLocationRequest =  LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            if (checkPermissionForLocation(this@MapActivity)) {
                startLocationUpdates()
            }

            mapView.start(object : MapLifeCycleCallback() {
                    override fun onMapDestroy() {
                        // 지도 API 가 정상적으로 종료될 때 호출됨
                        Log.d("확인", "정상 종료")
                    }

                    override fun onMapError(error: Exception) {
                        // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
                        Log.d("확인", "인증 실패 및 에러 $error")
                    }
                },
                object : KakaoMapReadyCallback() {
                    override fun onMapReady(kakaoMap: KakaoMap) {
                        // 인증 후 API 가 정상적으로 실행될 때 호출됨
                        Log.d("확인", "정상 실행")
                    }


                    override fun getZoomLevel(): Int {
                        return 15
                    }
            })
        }
    }


    private fun startLocationUpdates() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            onLocationChanged(locationResult.lastLocation!!)
        }
    }

    fun onLocationChanged(location: Location) {
        Toast.makeText(this@MapActivity, "${location.latitude}, ${location.longitude}", Toast.LENGTH_SHORT).show()
    }


    private fun checkPermissionForLocation(context: Context): Boolean {
        return if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
            false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()

            } else {
                Toast.makeText(this, "권한이 없어 지도를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }



}