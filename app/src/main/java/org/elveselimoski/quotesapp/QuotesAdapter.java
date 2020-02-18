package org.elveselimoski.quotesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.elveselimoski.quotesapp.model.Quote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvedin Selimoski on 2/18/2020.
 */
public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {

    private List<Quote> quotes;

    public QuotesAdapter(List<Quote> quotes) {
        this.quotes = quotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quotes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quote quote = quotes.get(holder.getAdapterPosition());
        holder.tvQuote.setText(quote.getEn());
        holder.tvAuthor.setText(quote.getAuthor());
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuote;
        TextView tvAuthor;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuote = itemView.findViewById(R.id.tvQuote);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
        }
    }
}
