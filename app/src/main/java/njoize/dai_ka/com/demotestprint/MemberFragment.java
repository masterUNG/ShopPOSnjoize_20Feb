package njoize.dai_ka.com.demotestprint;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemberFragment extends Fragment implements SearchView.OnQueryTextListener {

    private MyConstant myConstant;
    private ArrayList<String> nameMemberStringArrayList;
    ArrayList<NameMemberModel> nameMemberModelArrayList = new ArrayList<NameMemberModel>();
    private SearchView searchView;
    private MemberListViewAdapter memberListViewAdapter;
    private ArrayList<String> idStringArrayList;


    public MemberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myConstant = new MyConstant();
        nameMemberStringArrayList = new ArrayList<>();
        idStringArrayList = new ArrayList<>();

//        Create RecyclerView
        createRecyclerView();

    }   // Main Method

    private void createRecyclerView() {
        try {

            GetAllData getAllData = new GetAllData(getActivity());
            getAllData.execute(myConstant.getUrlGetAllMember());
            String json = getAllData.get();

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameMemberStringArrayList.add(jsonObject.getString("sname") + " " + jsonObject.getString("tel"));
                idStringArrayList.add(jsonObject.getString("id"));
            }
            Log.d("20FebV1", "nameMember ==> " + nameMemberStringArrayList.toString());



            for (int i = 0; i < nameMemberStringArrayList.size(); i += 1) {
                NameMemberModel nameMemberModel = new NameMemberModel(idStringArrayList.get(i), nameMemberStringArrayList.get(i));
                nameMemberModelArrayList.add(nameMemberModel);
            }

            ListView listView = getView().findViewById(R.id.listViewMember);
            memberListViewAdapter = new MemberListViewAdapter(getActivity(), nameMemberModelArrayList);

            listView.setAdapter(memberListViewAdapter);

            searchView = getView().findViewById(R.id.searchViewMember);
            searchView.setOnQueryTextListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member, container, false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        memberListViewAdapter.filter(text);
        return false;
    }
}
