package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    /** Required Feature from review */

    /**You will need inflate and add Menu to show the filters for today, week and saved. Currently,
     * saved and week filter selection should return the entire list of Asteroids, but later, if you
     * customize the fetching interval to more than a week, then returned data for week and all
     * filter would be different.
    You can use Transformations.switchMap to get a new instance of the LiveData to be observed. You
    can have a private MutableLiveData to hold the filter type which can be passed to switchMap
    source parameter and whenever user selects filter, switchMap will get triggered and you can
    return the appropriate LiveData from your Database DAO. You can assign the return value of
    switchMap to a single property in the ViewModel which can be observed once and the new data can
    be submitted to list😃*/





    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onViewCreated(), which we
     * do in this Fragment.
     */
    private val mainViewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.mainViewModel = mainViewModel

        binding.asteroidRecycler.adapter = AsteroidsRecyclerViewAdapter(AsteroidsRecyclerViewAdapter.OnClickListener {
            mainViewModel.navigateToDetailsScreen(it.id)
        }   )

        mainViewModel.eventNavigateToDetailsScreen.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(MainFragmentDirections.actionShowDetail(mainViewModel.idToNavigate.value))
                mainViewModel.onNavigateToDetailsScreen()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
