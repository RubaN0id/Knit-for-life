package ru.knitforlife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint
import ru.knitforlife.databinding.ActivityMainBinding
import ru.knitforlife.freagments.CameraFragment
import ru.knitforlife.freagments.CollectionFragment
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var cameraFragment:CameraFragment
    @Inject lateinit var collectionFragment: CollectionFragment
    lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fragmentContainerViewTag.visibility=View.GONE

        binding.toCameraButton.setOnClickListener {

            supportFragmentManager.beginTransaction()
                .add(binding.fragmentContainerViewTag.id, cameraFragment)
                .addToBackStack("camera")
                .commit()
            binding.fragmentContainerViewTag.visibility=View.VISIBLE
        }


        binding.toCollectionActivityButton.setOnClickListener {

            supportFragmentManager.beginTransaction()
                .add(binding.fragmentContainerViewTag.id, collectionFragment)
                .addToBackStack("collection")
                .commit()
            binding.fragmentContainerViewTag.visibility=View.VISIBLE
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val count = supportFragmentManager.backStackEntryCount
            if (count == 0) {
                binding.fragmentContainerViewTag.visibility=View.GONE
            }
        }
    }


}