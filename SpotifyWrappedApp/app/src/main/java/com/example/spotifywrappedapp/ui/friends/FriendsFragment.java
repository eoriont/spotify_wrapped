package com.example.spotifywrappedapp.ui.friends;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

<<<<<<< HEAD
=======
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
>>>>>>> 523cd92 (convert friends activity into fragment and mvvm)
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.spotifywrappedapp.databinding.FragmentFriendsBinding;

import java.util.ArrayList;
import java.util.List;

=======
import android.widget.Button;
import android.widget.EditText;

import com.example.spotifywrappedapp.R;
import com.example.spotifywrappedapp.databinding.FragmentFriendsBinding;

>>>>>>> 523cd92 (convert friends activity into fragment and mvvm)
public class FriendsFragment extends Fragment {

    private FriendsViewModel mViewModel;
    private FragmentFriendsBinding binding;
    private List<String> friends = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize ViewModel
        mViewModel = new ViewModelProvider(this).get(FriendsViewModel.class);
        // Inflate the layout for this fragment using View Binding
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = binding.lvItems;

        // Initialize the adapter with the current array (empty at this point)
        adapter = new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_list_item_1,
                                    friends);

        // Set the adapter to the ListView
        listView.setAdapter(adapter);


        // Observe the LiveData from the ViewModel
        mViewModel.getFriends().observe(getViewLifecycleOwner(), newList -> {
            // When data in LiveData changes, update the array
            friends = newList;

            // Notify the adapter of the data change
            adapter.clear();
            adapter.addAll(friends);
            adapter.notifyDataSetChanged();
        });


        // Setup UI components
        EditText enterFriend = binding.enterFriend;

        // Set click listener to update username in ViewModel
        binding.btnNew.setOnClickListener(v -> {
            String username = enterFriend.getText().toString();
            mViewModel.addFriend(username);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clean up binding when the view is destroyed
    }

}
