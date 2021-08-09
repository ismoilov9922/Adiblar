package uz.pdp.adiblar.ui

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import uz.pdp.adiblar.MainActivity
import uz.pdp.adiblar.R
import uz.pdp.adiblar.databinding.FragmentSettingBinding
import uz.pdp.adiblar.databinding.ModuleRemoveDialogBinding
import uz.pdp.adiblar.sharedPreference.YourPreference

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentSettingBinding
    lateinit var yourPreference: YourPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        (activity as MainActivity?)?.showBottomNavBar()
        yourPreference = YourPreference.getInstance(binding.root.context)
        if (yourPreference.getData("theme") == false) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.themeSwitch.isChecked = false
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.themeSwitch.isChecked = true
        }
        binding.themeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.themeSwitch.isChecked = true
                yourPreference.saveData("theme", true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.themeSwitch.isChecked = false
                yourPreference.saveData("theme", false)
            }
        }
        binding.share.setOnClickListener {
            intentMessageTelegram("https://play.google.com/store/apps/details?id=uz.mobiler.adiblar")
        }
        binding.info.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val binding1 = ModuleRemoveDialogBinding.inflate(inflater, null, false)
            builder.setView(binding1.root)
            val alertDialog = builder.create()
            alertDialog.show()
        }
        return binding.root
    }

    fun intentMessageTelegram(msg: String?) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Adiblar xayoti va ijodi")
        intent.putExtra(Intent.EXTRA_TEXT, msg)
        context?.startActivity(Intent.createChooser(intent, "choose one"))
    }

}