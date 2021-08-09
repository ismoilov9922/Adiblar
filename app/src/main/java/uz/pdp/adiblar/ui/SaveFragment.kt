package uz.pdp.adiblar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import uz.pdp.adiblar.R
import uz.pdp.adiblar.adapter.RvAdapter
import uz.pdp.adiblar.databinding.FragmentSaveBinding
import uz.pdp.adiblar.models.AdibData

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SaveFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentSaveBinding
    lateinit var rvAdapter: RvAdapter
    lateinit var list: ArrayList<AdibData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSaveBinding.inflate(layoutInflater)
        list = ArrayList()
        FirebaseFirestore.getInstance().collection("Adiblar").get()
            .addOnSuccessListener { result ->
                list.clear()
                for (document in result) {
                    if (document != null) {
                        val adibData = document.toObject(AdibData::class.java)
                        if (adibData.getSaved() == true) {
                            list.add(adibData)
                        }
                    }
                }
                rvAdapter = RvAdapter(list, object : RvAdapter.OnItemClickListener {
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
                            list.remove(adibData)
                            imageView.setImageResource(R.drawable.ic_vector__23_)
                            adibData.setSaved(false)
                            rvAdapter.notifyItemRemoved(position)
                            rvAdapter.notifyItemRangeChanged(position, list.size)
                        } else {
                            imageView.setImageResource(R.drawable.ic_vector__25_)
                            adibData.setSaved(true)
                            rvAdapter.notifyItemChanged(position)
                        }
                        FirebaseFirestore.getInstance().collection("Adiblar")
                            .document(adibData.getId().toString()).set(adibData)
                    }
                })
                binding.rv.adapter = rvAdapter
                binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        rvAdapter.filter.filter(newText)
                        return true
                    }
                })
            }
        return binding.root
    }
}