package com.example.escapetrapp.utils.featuretoggle

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.example.escapetrapp.BuildConfig
import com.example.escapetrapp.utils.firebase.RemoteConfigUtils
import com.example.escapetrapp.utils.firebase.RemoteConfigKeys
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FeatureToggleHelper {

    fun configureFeature(featureName: String, featureToggleListener: FeatureToggleListener) {
        setFeatureToggleListener(featureName, featureToggleListener)
    }

    private fun getFeatureToggleState(featureName: String?): FeatureToggleState {
        if (featureName == null) {
            return FeatureToggleState.INVISIBLE
        } else {
            return try {
                val featureConfig = getFeatureConfig(featureName)
                return featureConfig.status
            } catch (e: Exception) {
                FeatureToggleState.INVISIBLE
            }
        }
    }

    private fun setFeatureToggleListener( featureName: String, featureToggleListener: FeatureToggleListener){
        when (getFeatureToggleState(featureName)) {
            FeatureToggleState.INVISIBLE -> {
                featureToggleListener.onInvisible()
            }
            FeatureToggleState.ENABLED -> {
                featureToggleListener.onEnabled()
            }
            FeatureToggleState.DISABLED -> {
                featureToggleListener.onDisabled(this::showMessageUnavailable)
            }
        }
    }

    private fun getFeatureConfig(featureName: String): FeatureConfig {
        val gsonType = object : TypeToken<HashMap<String, FeatureConfig>>() {}.type
        val json = RemoteConfigUtils.getFirebaseRemoteConfig().getString(RemoteConfigKeys.FEATURE_CONFIG)
        val featureConfig = Gson().fromJson<HashMap<String, FeatureConfig>>(json, gsonType)[featureName]
        return featureConfig ?: getDefaultFeatureConfig()
    }

    private fun getDefaultFeatureConfig(): FeatureConfig {
        return FeatureConfig(
            BuildConfig.VERSION_CODE + 1, BuildConfig.VERSION_CODE + 1,
            FeatureToggleState.INVISIBLE )
    }
    private fun showMessageUnavailable(ctx: Context) {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("Desculpe o imprevisto!")
        builder.setMessage("Funcionalidade temporariamente indisponível")
        builder.setPositiveButton(android.R.string.yes) {
                dialog, which -> Toast.makeText( ctx, android.R.string.yes, Toast.LENGTH_SHORT ).show()
        }
        builder.show()
    }
}