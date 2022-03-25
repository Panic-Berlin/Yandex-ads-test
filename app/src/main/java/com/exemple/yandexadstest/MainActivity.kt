package com.exemple.yandexadstest

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exemple.yandexadstest.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.common.MobileAds
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val yandexAdWorker = App.instance.yandexAdWorker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeAds()
        showBannerAd()
        showInterstitialAd()
        showRewardedAd()
    }

    private fun initializeAds(){
        MobileAds.initialize(this){
            yandexAdWorker.startAdWorker(this)
            Log.d("Yandex Ads", "onCreate: Ads initialize")
        }
    }

    private fun showBannerAd(){
        if(!yandexAdWorker.bannerIsVisible){
            yandexAdWorker.loadBannerIntoContainer(viewBinding.banner)
        }
    }

    private fun showInterstitialAd(){
        viewBinding.btnInterstitial.setOnClickListener {
            yandexAdWorker.showInter()
        }
    }

    private fun showRewardedAd(){
        viewBinding.btnReward.setOnClickListener {
            yandexAdWorker.showRewarded()
        }
    }
}
