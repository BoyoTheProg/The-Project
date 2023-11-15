package com.movieapp.init;

import com.movieapp.model.entity.Plan;
import com.movieapp.model.enums.SubscriptionType;
import com.movieapp.repo.PlanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PlansInit implements CommandLineRunner {
    private final PlanRepository planRepository;

    public PlansInit(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = planRepository.count();

        if (count == 0){
            List<Plan> plans = new ArrayList<>();

            Arrays.stream(SubscriptionType.values())
                    .forEach(subscriptionType -> {
                        Plan plan = new Plan();
                        plan.setName(subscriptionType);
                        plans.add(plan);
                    });

            planRepository.saveAll(plans);
        }
    }
}
