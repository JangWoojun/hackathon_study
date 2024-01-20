package com.woojun.hackathonstudy

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.woojun.hackathonstudy.databinding.ActivityMapBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10
    private var kakaoMap: KakaoMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            mLocationRequest =  LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
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
                        Log.d("확인", "정상 실행")
                        this@MapActivity.kakaoMap = kakaoMap
                        if (checkPermissionForLocation(this@MapActivity)) {
                            startLocationUpdates()
                        }
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
        val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(location.latitude, location.longitude))
        kakaoMap!!.moveCamera(cameraUpdate)

        val styles = kakaoMap!!.labelManager!!
            .addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.human)))
        val options: LabelOptions =
            LabelOptions.from(LatLng.from(location.latitude, location.longitude)).setStyles(styles)
        val layer = kakaoMap!!.labelManager!!.layer
        val label = layer!!.addLabel(options)

        label.show()

        getMapResult(location.longitude.toString(), location.latitude.toString())
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

    private fun getMapResult(x: String, y: String) {
        val retrofitAPI = RetrofitClient.getInstance().create(RetrofitAPI::class.java)
        val call: Call<MapResult> = retrofitAPI.getMapResult(BuildConfig.REST_API_KEY,  "고등학교", x, y)

        call.enqueue(object : Callback<MapResult> {
            override fun onResponse(call: Call<MapResult>, response: Response<MapResult>) {
                if (response.isSuccessful) {
                    response.body()!!.documents.forEach {
                        val styles = kakaoMap!!.labelManager!!
                            .addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.backpack).setTextStyles(32, Color.BLACK,100, Color.WHITE)))
                        val options: LabelOptions =
                            LabelOptions.from(LatLng.from(it.y.toDouble(), it.x.toDouble()))
                                .setStyles(styles).setTexts(it.place_name)

                        val layer = kakaoMap!!.labelManager!!.layer
                        val label = layer!!.addLabel(options)

                        label.show()
                    }
                } else {
                    Log.d("확인", response.body().toString())
                }
            }

            override fun onFailure(call: Call<MapResult>, t: Throwable) {
                Toast.makeText(this@MapActivity, "지도를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}