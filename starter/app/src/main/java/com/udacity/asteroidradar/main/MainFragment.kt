package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.repository.AsteroidsFilter

class MainFragment : Fragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onViewCreated(), which we
     * do in this Fragment.
     */
    private val mainViewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(
            this,
            MainViewModel.Factory(activity.application)
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.mainViewModel = mainViewModel

        binding.asteroidRecycler.adapter =
            AsteroidsRecyclerViewAdapter(AsteroidsRecyclerViewAdapter.OnClickListener {
                mainViewModel.navigateToDetailsScreen(it.id)
            })

        mainViewModel.eventNavigateToDetailsScreen.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(mainViewModel.idToNavigate.value))
                mainViewModel.onNavigateToDetailsScreen()
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mainViewModel.getAsteroidsBriefs(
            when (item.itemId) {
                R.id.show_week -> AsteroidsFilter.SHOW_WEEK
                R.id.show_today -> AsteroidsFilter.SHOW_TODAY
                else -> AsteroidsFilter.SHOW_SAVED
            }
        )
        return true
    }
}
