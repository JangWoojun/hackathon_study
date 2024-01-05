package com.woojun.hackathonstudy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.woojun.hackathonstudy.databinding.ActivityMapBinding


class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
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

                    override fun getPosition(): LatLng {
                        // 지도 시작 시 위치 좌표를 설정
                        return LatLng.from(37.6275889, 126.9231578)
                    }

                    override fun getZoomLevel(): Int {
                        // 지도 시작 시 확대/축소 줌 레벨 설정
                        return 15
                    }
            })

        }
    }
}