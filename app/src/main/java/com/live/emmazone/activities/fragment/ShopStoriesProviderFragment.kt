package com.live.emmazone.activities.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.common.internal.service.Common
import com.live.emmazone.R
import com.live.emmazone.activities.ImageGetter
import com.live.emmazone.activities.provider.AddShopStory
import com.live.emmazone.adapter.ShopStoryProviderAdapter
import com.live.emmazone.databinding.FragmentShopStoriesProviderBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.model.ModelShopStory
import com.live.emmazone.net.RestObservable
import com.live.emmazone.net.Status
import com.live.emmazone.response_model.CommonResponse
import com.live.emmazone.response_model.ShopStoryResponse
import com.live.emmazone.utils.AppConstants
import com.live.emmazone.utils.AppUtils
import com.live.emmazone.utils.ImagePickerUtility
import com.live.emmazone.view_models.AppViewModel
import com.schunts.extensionfuncton.toBody
import okhttp3.RequestBody

class ShopStoriesProviderFragment : Fragment(), Observer<RestObservable> {

    private lateinit var binding: FragmentShopStoriesProviderBinding
    private lateinit var adapter : ShopStoryProviderAdapter
    private val list =  ArrayList<ModelShopStory>()
    private val appViewModel: AppViewModel by viewModels()
    private var position = -1
    private var count = 0
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imagePath =  result.data?.getStringExtra("imagePath")
                if(!imagePath.isNullOrEmpty()){
                    val intent = Intent(requireContext(),AddShopStory::class.java)
                    intent.putExtra("imagePath",imagePath)
                    count = 1
                    startActivity(intent)
                }
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopStoriesProviderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d("resume",count.toString())

        if(count == 1){
            count++
        }
        else if(count == 2){
            loadStories()
            count = 0
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.addStory.setOnClickListener {
            launcher.launch(Intent(requireContext(), ImageGetter::class.java))
        }
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        adapter = ShopStoryProviderAdapter(requireContext(),list){
            position = it
            deleteStories(list[it].id)
        }
        binding.recyclerView.adapter = adapter
        loadStories()
    }
    private fun loadStories(){
        val vendorId = getPreference(AppConstants.VENDOR_ID,"")
        val map = HashMap<String,RequestBody>()
        map["vendorId"] = toBody(vendorId)
        appViewModel.getShopStories(requireActivity(),true,map)
        appViewModel.mResponse.observe(requireActivity(),this)
    }
    private fun deleteStories(id:Int) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        dialog.setContentView(R.layout.dialog_delete_story)

        val yesBtn: Button = dialog.findViewById(R.id.btnCancelYes)
        val noBtn: Button = dialog.findViewById(R.id.btnCancelNo)

        yesBtn.setOnClickListener {
            dialog.dismiss()
            confirmDelete(id)
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
    private fun confirmDelete(id:Int){
        val map = HashMap<String, RequestBody>()
        map["id"] = toBody(id.toString())
        appViewModel.deleteShopStories(requireActivity(), true, map)
        appViewModel.mResponse.observe(requireActivity(), this)
    }
    override fun onChanged(t: RestObservable?) {
        when(t!!.status){
            Status.SUCCESS->{
                if(t.data is ShopStoryResponse){
                    if(t.data.body.size > 0){
                        list.clear()
                        list.addAll(t.data.body[0].stories)
                        adapter.notifyDataSetChanged()
                        if(list.size == 0){
                            binding.toHide.visibility = View.VISIBLE
                        }
                        else{
                            binding.toHide.visibility = View.GONE
                        }
                    }
                    else{
                        binding.toHide.visibility = View.VISIBLE
                    }
                }
                else if(t.data is CommonResponse){
                    if(position>=0 && position<list.size){
                        list.removeAt(position)
                        adapter.notifyItemRemoved(position)
                    }
                    AppUtils.showMsgOnlyWithoutClick(requireActivity(),t.data.message)
                }
            }
            Status.ERROR->{
                AppUtils.showMsgOnlyWithoutClick(requireContext(),t.error.toString())
            }
            else->{}
        }
    }
}