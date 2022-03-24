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

    private val bannerId = "R-M-DEMO-320x50"
    private val interstitialId = "R-M-DEMO-320x480"
    private val YANDEX_BLOCK_ID = "adf-279013/966487"
    var interstitialLoaded = false
    var rewardLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this){
            Log.d("Yandex Ads", "onCreate: Ads initialize")
        }
        interstitialAd()
        bannerAd()
        rewardedAd()
    }

    private fun rewardedAd() {
        val rewardedAd = RewardedAd(this)
        rewardedAd.setAdUnitId(YANDEX_BLOCK_ID)
        val adRequest = AdRequest.Builder().build()
        rewardedAd.setRewardedAdEventListener(object : RewardedAdEventListener{
            override fun onAdLoaded() {
                Log.d(TAG, "onAdLoadedRewarded: load")
                rewardLoaded = true
            }

            override fun onAdFailedToLoad(p0: AdRequestError) {
                val snackbar =
                    Snackbar.make(viewBinding.parent, "При загрузке Rewarded рекламы произошла ошибка", 3000)
                snackbar.anchorView = null
                snackbar.show()
                Log.d(TAG, "onAdFailedToLoadRewarded: $p0")
            }

            override fun onAdShown() {
                Log.d(TAG, "onAdShownRewarded: shown")
            }

            override fun onAdDismissed() {
                Log.d(TAG, "onAdDismissedRewarded: dismissed")
            }

            override fun onRewarded(p0: Reward) {
                Log.d(TAG, "onRewarded: $p0")
            }

            override fun onAdClicked() {
                Log.d(TAG, "onAdClickedReward: click")
            }

            override fun onLeftApplication() {
                Log.d(TAG, "onLeftApplicationReward: left app")
            }

            override fun onReturnedToApplication() {
                Log.d(TAG, "onReturnedToApplicationReward: return to app")
            }

            override fun onImpression(p0: ImpressionData?) {
                Log.d(TAG, "onImpressionReward: $p0")
            }

        })
        rewardedAd.loadAd(adRequest)
        viewBinding.btnReward.setOnClickListener {
            if (rewardLoaded){
                rewardedAd.show()
            }else{
                val snackbar =
                    Snackbar.make(viewBinding.parent, "При загрузке Interstitial рекламы произошла ошибка", 3000)
                snackbar.anchorView = null
                snackbar.show()
            }
        }
    }

    private fun interstitialAd() {
        val mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.setAdUnitId(interstitialId)
        val adRequest = AdRequest.Builder().build()
        mInterstitialAd.setInterstitialAdEventListener(object : InterstitialAdEventListener{
            override fun onAdLoaded() {
                interstitialLoaded = true
            }

            override fun onAdFailedToLoad(p0: AdRequestError) {
                val snackbar =
                    Snackbar.make(viewBinding.parent, "При загрузке Interstitial рекламы произошла ошибка", 3000)
                snackbar.anchorView = null
                snackbar.show()
                Log.d(TAG, "onAdFailedToLoadInterstitial: $p0")
            }

            override fun onAdShown() {
                Log.d(TAG, "onAdShownInterstitial: Ad show")
            }

            override fun onAdDismissed() {
                Log.d(TAG, "onAdDismissedInterstitial: dismiss")
            }

            override fun onAdClicked() {
                Log.d(TAG, "onAdClickedInterstitial: click")
            }

            override fun onLeftApplication() {
                Log.d(TAG, "onLeftApplicationInterstitial: Left app")
            }

            override fun onReturnedToApplication() {
                Log.d(TAG, "onReturnedToApplicationInterstitial: Return to app")
            }

            override fun onImpression(p0: ImpressionData?) {
                Log.d(TAG, "onImpression: $p0")
            }

        })
        mInterstitialAd.loadAd(adRequest)
        viewBinding.btnInterstitial.setOnClickListener {
            if (interstitialLoaded){
                mInterstitialAd.show()
            }else{
                val snackbar =
                    Snackbar.make(viewBinding.parent, getString(R.string.show_ad_error), 3000)
                snackbar.anchorView = null
                snackbar.show()
            }
        }
    }



    private fun bannerAd(){
        val banner = findViewById<BannerAdView>(R.id.banner)
        banner.setAdUnitId(bannerId)
        banner.setAdSize(AdSize.BANNER_320x50)
        banner.setBannerAdEventListener(object : BannerAdEventListener{
            override fun onAdLoaded() {
                Log.d(TAG, "onAdLoadedBanner: load")
            }

            override fun onAdFailedToLoad(p0: AdRequestError) {
                Log.d(TAG, "onAdFailedToLoadBanner: $p0")
            }

            override fun onAdClicked() {
                Log.d(TAG, "onAdClickedBanner: click")
            }

            override fun onLeftApplication() {
                Log.d(TAG, "onLeftApplicationBanner: Left app")
            }

            override fun onReturnedToApplication() {
                Log.d(TAG, "onReturnedToApplicationBanner: Return to app")
            }

            override fun onImpression(p0: ImpressionData?) {
                Log.d(TAG, "onImpressionBanner: $p0")
            }

        })

        val adRequest = AdRequest.Builder().build()

        banner.loadAd(adRequest)

    }
}
