package in.stackroute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reward {
  private int rewardId;
  private String description;
  private int pointsRequired;

  
}

