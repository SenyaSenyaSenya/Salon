import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.R

class ServiceAdapter(private val serviceList: ArrayList<Service>) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val serviceNameTextView: TextView = itemView.findViewById(R.id.serviceNameTextView)
        val serviceDescriptionTextView: TextView = itemView.findViewById(R.id.serviceDescriptionTextView)
        val serviceDurationTextView: TextView = itemView.findViewById(R.id.serviceDurationTextView)
        val servicePriceTextView: TextView = itemView.findViewById(R.id.servicePriceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_service, parent, false)
        return ServiceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val currentItem = serviceList[position]

        holder.serviceNameTextView.text = currentItem.name
        holder.serviceDescriptionTextView.text = currentItem.description
        holder.serviceDurationTextView.text = currentItem.duration
        holder.servicePriceTextView.text = currentItem.price
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }
}