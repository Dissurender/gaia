import { FormGroup, FormControlLabel, Switch } from "@mui/material";

const Preferences = () => {
  const dietary_preferences = [
    "Vegetarian",
    "Vegan",
    "Paleo",
    "Gluten-free diet",
    "Ketogenic",
    "Lactose-intolerant",
    "Halal",
    "Kosher",
    "Allergic to Nuts",
  ];

  return (
    <FormGroup>
      <FormControlLabel
        control={<Switch defaultChecked />}
        label="No allergies"
      />
      {dietary_preferences.map((diet) => (
        <FormControlLabel control={<Switch />} label={diet} key={diet} />
      ))}
    </FormGroup>
  );
};

export default Preferences;
