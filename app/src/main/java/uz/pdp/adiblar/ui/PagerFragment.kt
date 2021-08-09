package uz.pdp.adiblar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import uz.pdp.adiblar.R
import uz.pdp.adiblar.adapter.RvAdapter
import uz.pdp.adiblar.databinding.FragmentPagerBinding
import uz.pdp.adiblar.models.AdibData

private const val ARG_PARAM1 = "param1"

class PagerFragment : Fragment() {
    private var param1: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    lateinit var binding: FragmentPagerBinding
    lateinit var rvAdapter: RvAdapter
    lateinit var adibList: ArrayList<AdibData>
    private val TAG = "PagerFragment"
    lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPagerBinding.inflate(layoutInflater)
        firebaseFirestore = FirebaseFirestore.getInstance()
        adibList = ArrayList()
        firebaseFirestore.collection("Adiblar").get()
            .addOnSuccessListener { result ->
                adibList.clear()
                for (document in result) {
                    if (document != null) {
                        val adibData = document.toObject(AdibData::class.java)
                        if (adibData.getCategory() == param1.toString()) {
                            adibList.add(adibData)
                        }
                    }
                }
                rvAdapter = RvAdapter(adibList, object : RvAdapter.OnItemClickListener {
                    override fun onItemMusic(writersClass: AdibData) {
                        val bundle = bundleOf("adib" to writersClass)
                        findNavController().navigate(R.id.infoFragment, bundle)
                    }

                    override fun onItemSaved(
                        imageView: ImageView,
                        position: Int,
                        adibData: AdibData,
                    ) {
                        if (adibData.getSaved() == true) {
                            imageView.setImageResource(R.drawable.ic_vector__23_)
                            adibData.setSaved(false)
                            rvAdapter.notifyItemChanged(position)
                        } else {
                            imageView.setImageResource(R.drawable.ic_vector__25_)
                            adibData.setSaved(true)
                            rvAdapter.notifyItemChanged(position)
                        }
                        firebaseFirestore.collection("Adiblar")
                            .document(adibData.getId().toString()).set(adibData)
                    }
                })
                binding.recView.adapter = rvAdapter
            }
        return binding.root
    }

    companion object {
        fun newInstance(param1: String) =
            PagerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}