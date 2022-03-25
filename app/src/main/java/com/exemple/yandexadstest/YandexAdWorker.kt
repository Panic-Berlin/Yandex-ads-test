package com.exemple.yandexadstest

import android.app.Application
import android.content.ContentValues
import android.util.Log
import android.view.View
import android.widget.Toast
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener

class YandexAdWorker(application: Application) {
    private var BANNER_BLOCK_ID = "R-M-DEMO-320x50" // TEST AD ID
    private var INTER_BLOCK_ID = "R-M-DEMO-320x480" // TEST AD ID
    private var REWARDED_BLOCK_ID = "adf-279013/966487" // TEST AD ID
    private val USE_TEST_ADS = true
    var bannerIsVisible = false
    var isInterstitialLoaded = false
    var isRewardedLoaded = false
    private val context = application
    private var mInterstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    init {
        if (!USE_TEST_ADS){
            BANNER_BLOCK_ID = "" // NOT TEST AD ID
            INTER_BLOCK_ID = "" // NOT TEST AD ID
            REWARDED_BLOCK_ID = ""  // NOT TEST AD ID
        }
    }

    /**
     * Запуск загрузки рекламы с вознагрождением
     * и полноэкранной рекламы
     */
    fun startAdWorker(mainActivity: MainActivity){
        mInterstitialAd = InterstitialAd(mainActivity)
        rewardedAd = RewardedAd(mainActivity)
        loadInterstitial()
        loadRewarded()
    }

    /**
     * Загрузка рекламы в баннере
     */
    fun loadBannerIntoContainer(bannerAdView: BannerAdView) {
        bannerAdView.visibility = View.INVISIBLE
        bannerAdView.setAdUnitId(BANNER_BLOCK_ID)
        bannerAdView.setAdSize(AdSize.BANNER_320x50) // Banner size. Default 320x50
        bannerAdView.setBannerAdEventListener(object : BannerAdEventListener {
            override fun onAdLoaded() {
                bannerAdView.visibility = View.VISIBLE
                bannerIsVisible = true
                Log.d(ContentValues.TAG, "onAdLoadedBanner: load")
            }

            override fun onAdFailedToLoad(p0: AdRequestError) {
                Toast.makeText(context, "При загрузке Banner произошла ошибка", Toast.LENGTH_SHORT).show()
                Log.d(ContentValues.TAG, "onAdFailedToLoadBanner: $p0")
            }

            override fun onAdClicked() {
                Log.d(ContentValues.TAG, "onAdClickedBanner: click")
            }

            override fun onLeftApplication() {
                Log.d(ContentValues.TAG, "onLeftApplicationBanner: Left app")
            }

            override fun onReturnedToApplication() {
                Log.d(ContentValues.TAG, "onReturnedToApplicationBanner: Return to app")
            }

            override fun onImpression(p0: ImpressionData?) {
                Log.d(ContentValues.TAG, "onImpressionBanner: $p0")
            }

        })
        val adRequest = AdRequest.Builder().build()
        bannerAdView.loadAd(adRequest)
    }

    /**
     * Загрузка полноэкранной рекламы
     */
    private fun loadInterstitial(){
        mInterstitialAd!!.setAdUnitId(INTER_BLOCK_ID)
        val adRequest = AdRequest.Builder().build()
        mInterstitialAd!!.setInterstitialAdEventListener(object : InterstitialAdEventListener {
            override fun onAdLoaded() {
                isInterstitialLoaded = true
            }

            override fun onAdFailedToLoad(p0: AdRequestError) {
                isInterstitialLoaded = false
                Log.d(ContentValues.TAG, "onAdFailedToLoad: $p0")
            }

            override fun onAdShown() {
                Log.d(ContentValues.TAG, "onAdShownInterstitial: Ad show")
            }

            override fun onAdDismissed() {
                Log.d(ContentValues.TAG, "onAdDismissedInterstitial: dismiss")
            }

            override fun onAdClicked() {
                Log.d(ContentValues.TAG, "onAdClickedInterstitial: click")
            }

            override fun onLeftApplication() {
                Log.d(ContentValues.TAG, "onLeftApplicationInterstitial: Left app")
            }

            override fun onReturnedToApplication() {
                Log.d(ContentValues.TAG, "onReturnedToApplicationInterstitial: Return to app")
            }

            override fun onImpression(p0: ImpressionData?) {
                Log.d(ContentValues.TAG, "onImpression: $p0")
            }

        })
        mInterstitialAd!!.loadAd(adRequest)
    }

    /**
     * Демонстрация полнжкранной рекламы
     */
    fun showInter(){
        if(isInterstitialLoaded){
            mInterstitialAd!!.show()
        }else{
            Toast.makeText(context, "При загрузке Interstitial произошла ошибка", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Загрузка рекламы с вознаграждением
     */
    private fun loadRewarded(){
        rewardedAd!!.setAdUnitId(REWARDED_BLOCK_ID)
        val adRequest = AdRequest.Builder().build()
        rewardedAd!!.setRewardedAdEventListener(object : RewardedAdEventListener {
            override fun onAdLoaded() {
                Log.d(ContentValues.TAG, "onAdLoadedRewarded: load")
                isRewardedLoaded = true
            }

            override fun onAdFailedToLoad(p0: AdRequestError) {
                isRewardedLoaded = false
            }

            override fun onAdShown() {
                Log.d(ContentValues.TAG, "onAdShownRewarded: shown")
            }

            override fun onAdDismissed() {
                Log.d(ContentValues.TAG, "onAdDismissedRewarded: dismissed")
            }

            override fun onRewarded(p0: Reward) {
                Log.d(ContentValues.TAG, "onRewarded: $p0")
            }

            override fun onAdClicked() {
                Log.d(ContentValues.TAG, "onAdClickedReward: click")
            }

            override fun onLeftApplication() {
                Log.d(ContentValues.TAG, "onLeftApplicationReward: left app")
            }

            override fun onReturnedToApplication() {
                Log.d(ContentValues.TAG, "onReturnedToApplicationReward: return to app")
            }

            override fun onImpression(p0: ImpressionData?) {
                Log.d(ContentValues.TAG, "onImpressionReward: $p0")
            }

        })
        rewardedAd!!.loadAd(adRequest)
    }

    /**
     * Демонстрация рекламы с вознаграждением
     */
    fun showRewarded(){
        if (isRewardedLoaded){
            rewardedAd!!.show()
        }else{
            Toast.makeText(context, "При загрузке Reward произошла ошибка", Toast.LENGTH_SHORT).show()
        }
    }
}
