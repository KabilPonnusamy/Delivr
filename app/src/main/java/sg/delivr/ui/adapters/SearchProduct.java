package sg.delivr.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sg.delivr.R;
import sg.delivr.ui.models.SearchDatas;

public class SearchProduct extends ArrayAdapter<SearchDatas> {

    Context context;
    int resource, textViewResourceId;
    List<SearchDatas> items, tempItems, suggestions;

    public SearchProduct(Context context, int resource, int textViewResourceId, List<SearchDatas> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<SearchDatas>(items); // this makes the difference.
        suggestions = new ArrayList<SearchDatas>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ly_search_item, parent, false);
        }
        SearchDatas people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.service_name);
            if (lblName != null)
                lblName.setText(people.getLoc_name());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((SearchDatas) resultValue).getLoc_name();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (SearchDatas people : tempItems) {

                    if (people.getLoc_name().contains(constraint.toString())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<SearchDatas> filterList = (ArrayList<SearchDatas>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (SearchDatas people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
