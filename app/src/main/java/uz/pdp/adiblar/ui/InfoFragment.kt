package uz.pdp.adiblar.ui

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import uz.pdp.adiblar.MainActivity
import uz.pdp.adiblar.databinding.FragmentInfoBinding
import uz.pdp.adiblar.models.AdibData

class InfoFragment : Fragment() {
    lateinit var binding: FragmentInfoBinding
    lateinit var obj: AdibData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        (activity as MainActivity?)?.hideBottomNavBar()
        if (arguments != null) {
            obj = arguments?.get("adib") as AdibData
        }
        Picasso.get().load(obj.getImgUrl()).into(binding.image)
        binding.txt.text = obj.getDescription()
        binding.date.text = obj.getDateOfBirth()
        binding.fullname.text = obj.getFullName()
        binding.dash.title = obj.getFullName()
        binding.button.setOnClickListener(View.OnClickListener {
            val criteria: String = binding.edit.text.toString()
            var fullText: String = binding.txt.text.toString()
            if (fullText.toLowerCase().contains(criteria.toLowerCase())) {
                val indexOfCriteria = fullText.indexOf(criteria)
                val lineNumber: Int = binding.txt.layout.getLineForOffset(indexOfCriteria)
                val highlighted = "<font color='yellow'>$criteria</font>"
                fullText = fullText.replace(criteria, highlighted)
                binding.txt.text = Html.fromHtml(fullText)
                binding.scrollView.scrollTo(0, binding.txt.layout.getLineTop(lineNumber))
            }
        })
        binding.edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.isEmpty()) {
                    var fullText: String = binding.txt.text.toString()
                    fullText = fullText.replace("<font color='red'>", "")
                    fullText = fullText.replace("</font>", "")
                    binding.txt.text = fullText
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}