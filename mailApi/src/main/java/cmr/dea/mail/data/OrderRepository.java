package cmr.dea.mail.data;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<CarrierOrderEntity,Long> {
}
