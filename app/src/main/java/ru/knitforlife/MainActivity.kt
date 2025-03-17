package ru.knitforlife

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import di.CameraComponent
import di.CameraComponentProvider
import fragment.CameraFragment
import ru.knitforlife.color.di.ColorCollectionComponent
import ru.knitforlife.color.di.ColorCollectionComponentProvider
import ru.knitforlife.color.fragments.CollectionFragment
import ru.knitforlife.databinding.ActivityMainBinding
import ru.knitforlife.di.DaggerMainActivityComponent
import ru.knitforlife.di.MainActivityComponent
import javax.inject.Inject


class MainActivity : AppCompatActivity(),CameraComponentProvider, ColorCollectionComponentProvider{
    @Inject
    lateinit var cameraFragment: CameraFragment
    @Inject
    lateinit var collectionFragment: CollectionFragment
    lateinit var binding: ActivityMainBinding

    private lateinit var _activityComponent: MainActivityComponent

    val activityComponent: MainActivityComponent
        get() = _activityComponent

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        _activityComponent = DaggerMainActivityComponent.factory().create(
            activityContext = this,
            applicationComponent = (application as App).appComponent
        )
        _activityComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fragmentContainerViewTag.visibility = View.GONE



        binding.toCameraButton.setOnClickListener {

            supportFragmentManager.beginTransaction()
                .add(binding.fragmentContainerViewTag.id, cameraFragment)
                .addToBackStack("camera")
                .commit()
            binding.fragmentContainerViewTag.visibility = View.VISIBLE
        }


        binding.toCollectionActivityButton.setOnClickListener {

            supportFragmentManager.beginTransaction()
                .add(binding.fragmentContainerViewTag.id, collectionFragment)
                .addToBackStack("collection")
                .commit()
            binding.fragmentContainerViewTag.visibility = View.VISIBLE
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val count = supportFragmentManager.backStackEntryCount
            if (count == 0) {
                binding.fragmentContainerViewTag.visibility = View.GONE
            }
        }
    }

    override fun provideCameraComponent(): CameraComponent {
        return _activityComponent.cameraComponent().create()
    }

    override fun provideColorCollectionComponent(): ColorCollectionComponent {
        return _activityComponent.colorCollectionComponent().create()
    }


}