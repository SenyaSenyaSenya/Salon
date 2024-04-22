import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.R
import com.example.salon.admin.services.EditServiceActivity

class ServiceAdapter(private val serviceList: ArrayList<Service>) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val serviceNameTextView: TextView = itemView.findViewById(R.id.serviceNameTextView)
        private val serviceDescriptionTextView: TextView = itemView.findViewById(R.id.serviceDescriptionTextView)
        private val serviceDurationTextView: TextView = itemView.findViewById(R.id.serviceDurationTextView)
        private val servicePriceTextView: TextView = itemView.findViewById(R.id.servicePriceTextView)
        private val deleteServiceImageView: ImageView = itemView.findViewById(R.id.deleteServiceImageView)

        fun bind(service: Service) {
            serviceNameTextView.text = service.name
            serviceDescriptionTextView.text = service.description
            serviceDurationTextView.text = service.duration
            servicePriceTextView.text = service.price
        }

        init {
            deleteServiceImageView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    serviceList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val service = serviceList[position]

                    val intent = Intent(itemView.context, EditServiceActivity::class.java)
                    intent.putExtra("serviceName", service.name)
                    intent.putExtra("serviceDescription", service.description)
                    intent.putExtra("serviceDuration", service.duration)
                    intent.putExtra("servicePrice", service.price)

                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_service, parent, false)
        return ServiceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val currentService = serviceList[position]
        holder.bind(currentService)
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }
}