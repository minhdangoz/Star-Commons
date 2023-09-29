package com.simplemobiletools.commons.samples.activities

import android.content.Intent
import android.os.Bundle
import com.simplemobiletools.commons.activities.BaseSimpleActivity
import com.simplemobiletools.commons.activities.ManageBlockedNumbersActivity
import com.simplemobiletools.commons.dialogs.BottomSheetChooserDialog
import com.simplemobiletools.commons.dialogs.CallConfirmationDialog
import com.simplemobiletools.commons.extensions.appLaunched
import com.simplemobiletools.commons.extensions.toast
import com.simplemobiletools.commons.extensions.viewBinding
import com.simplemobiletools.commons.helpers.LICENSE_AUTOFITTEXTVIEW
import com.simplemobiletools.commons.models.FAQItem
import com.simplemobiletools.commons.models.SimpleListItem
import com.simplemobiletools.commons.samples.BuildConfig
import com.simplemobiletools.commons.samples.R
import com.simplemobiletools.commons.samples.databinding.ActivityMainBinding

class MainActivity : BaseSimpleActivity() {
    override fun getAppLauncherName() = getString(R.string.smtco_app_name)

    override fun getAppIconIDs(): ArrayList<Int> {
        val ids = ArrayList<Int>()
        ids.add(R.mipmap.commons_launcher)
        return ids
    }

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appLaunched(BuildConfig.APPLICATION_ID)

        updateMaterialActivityViews(binding.mainCoordinator, binding.mainHolder, useTransparentNavigation = true, useTopSearchMenu = false)
        setupMaterialScrollListener(binding.mainNestedScrollview, binding.mainToolbar)

        binding.mainColorCustomization.setOnClickListener {
            startCustomizationActivity()
        }
        binding.bottomSheetChooser.setOnClickListener {
            launchAbout()
        }
        binding.manageBlockedNumbers.setOnClickListener {
            startActivity(Intent(this, ManageBlockedNumbersActivity::class.java))
        }
        binding.composeDialogs.setOnClickListener {
            startActivity(Intent(this, TestDialogActivity::class.java))
        }
    }

    private fun launchAbout() {
        val licenses = LICENSE_AUTOFITTEXTVIEW

        val faqItems = arrayListOf(
            FAQItem(com.mobilestartools.commons.R.string.faq_1_title_commons, com.mobilestartools.commons.R.string.faq_1_text_commons),
            FAQItem(com.mobilestartools.commons.R.string.faq_1_title_commons, com.mobilestartools.commons.R.string.faq_1_text_commons),
            FAQItem(com.mobilestartools.commons.R.string.faq_4_title_commons, com.mobilestartools.commons.R.string.faq_4_text_commons)
        )

        if (!resources.getBoolean(com.mobilestartools.commons.R.bool.hide_google_relations)) {
            faqItems.add(FAQItem(com.mobilestartools.commons.R.string.faq_2_title_commons, com.mobilestartools.commons.R.string.faq_2_text_commons))
            faqItems.add(FAQItem(com.mobilestartools.commons.R.string.faq_6_title_commons, com.mobilestartools.commons.R.string.faq_6_text_commons))
        }

        startAboutActivity(R.string.smtco_app_name, licenses, BuildConfig.VERSION_NAME, faqItems, true)
    }

    private fun launchBottomSheetDemo() {
        BottomSheetChooserDialog.createChooser(
            fragmentManager = supportFragmentManager,
            title = com.mobilestartools.commons.R.string.please_select_destination,
            items = arrayOf(
                SimpleListItem(1, com.mobilestartools.commons.R.string.record_video, com.mobilestartools.commons.R.drawable.ic_camera_vector),
                SimpleListItem(
                    2,
                    com.mobilestartools.commons.R.string.record_audio,
                    com.mobilestartools.commons.R.drawable.ic_microphone_vector,
                    selected = true
                ),
                SimpleListItem(4, com.mobilestartools.commons.R.string.choose_contact, com.mobilestartools.commons.R.drawable.ic_add_person_vector)
            )
        ) {
            toast("Clicked ${it.id}")
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar(binding.mainToolbar)
        CallConfirmationDialog(this, callee = "Simple Mobile Tools"){

        }
    }
}
