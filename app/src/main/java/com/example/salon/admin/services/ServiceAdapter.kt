import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.salon.R
import com.example.salon.admin.services.DatabaseServiceHelper
import com.example.salon.admin.services.EditServiceActivity

class ServiceAdapter(
    private val serviceList: ArrayList<Service>,
    private val databaseHelper: DatabaseServiceHelper
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {
    private var onItemClickListener: ((Service) -> Unit)? = null
    private var filteredServiceList: ArrayList<Service> = ArrayList(serviceList)
    private var filterQuery: String? = null
    private var sortAscending: Boolean = true

    // Функция для фильтрации списка услуг
    fun filterServices(query: String?) {
        filterQuery = query
        filteredServiceList.clear()
        if (query.isNullOrEmpty()) {
            filteredServiceList.addAll(serviceList)
        } else {
            for (service in serviceList) {
                if (service.name.contains(query, ignoreCase = true)) {
                    filteredServiceList.add(service)
                }
            }
        }
        notifyDataSetChanged()
    }

    // Функция для сортировки списка услуг
    fun sortServices(ascending: Boolean) {
        sortAscending = ascending
        filteredServiceList.sortBy { it.price.toDoubleOrNull() ?: 0.0 }
        if (!ascending) {
            filteredServiceList.reverse()
        }
        notifyDataSetChanged()
    }

    inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val serviceNameTextView: TextView = itemView.findViewById(R.id.serviceNameTextView)
        private val serviceDescriptionTextView: TextView =
            itemView.findViewById(R.id.serviceDescriptionTextView)
        private val serviceDurationTextView: TextView =
            itemView.findViewById(R.id.serviceDurationTextView)
        private val servicePriceTextView: TextView =
            itemView.findViewById(R.id.servicePriceTextView)
        internal val deleteServiceImageView: ImageView =
            itemView.findViewById(R.id.deleteServiceImageView)
        internal val editServiceImageView: ImageView =
            itemView.findViewById(R.id.editServiceImageView)
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
                    val service = serviceList[position]
                    val dialogBuilder = AlertDialog.Builder(itemView.context)
                    dialogBuilder.setTitle("Удаление услуги")
                    dialogBuilder.setMessage("Вы действительно хотите удалить эту услугу?")
                    dialogBuilder.setPositiveButton("Да") { _, _ ->
                        serviceList.remove(service)
                        notifyItemRemoved(position)
                        onItemClickListener?.invoke(service)
                    }
                    dialogBuilder.setNegativeButton("Нет", null)
                    val dialog = dialogBuilder.create()
                    dialog.show()
                }
            }

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val service = serviceList[position]
                    onItemClickListener?.invoke(service)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_service, parent, false)
        return ServiceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val currentService = serviceList[position]
        holder.bind(currentService)
        holder.editServiceImageView.setOnClickListener {
            val service = serviceList[position]
            val intent = Intent(holder.itemView.context, EditServiceActivity::class.java)
            intent.putExtra("serviceId", service.id)
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteServiceImageView.setOnClickListener {
            val service = serviceList[position]
            val dialogBuilder = AlertDialog.Builder(holder.itemView.context)
            dialogBuilder.setTitle("Удаление услуги")
            dialogBuilder.setMessage("Вы действительно хотите удалить эту услугу?")
            dialogBuilder.setPositiveButton("Да") { _, _ ->
                // Удаляем услугу из базы данных
                databaseHelper.deleteService(service.id)
                // Удаляем услугу из списка
                serviceList.remove(service)
                // Обновляем адаптер
                notifyDataSetChanged()
            }
            dialogBuilder.setNegativeButton("Нет", null)
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }

    fun setOnItemClickListener(listener: (Service) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return filteredServiceList.size
    }
}