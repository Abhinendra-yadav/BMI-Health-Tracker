$(document).ready(function() {
    $('#bmiForm').on('submit', function(e) {
        e.preventDefault(); 

        let weight = parseFloat($('#weight').val());
        let heightCm = parseFloat($('#height').val());
        let heightM = heightCm / 100;

        let bmi = (weight / (heightM * heightM)).toFixed(1);
        let category = '';
        let badgeClass = '';

        if (bmi < 18.5) {
            category = 'Underweight'; badgeClass = 'bg-underweight';
        } else if (bmi >= 18.5 && bmi < 24.9) {
            category = 'Normal Weight'; badgeClass = 'bg-normal';
        } else if (bmi >= 25 && bmi < 29.9) {
            category = 'Overweight'; badgeClass = 'bg-overweight';
        } else {
            category = 'Obese'; badgeClass = 'bg-obese';
        }

        $('#bmiValue').text(bmi);
        $('#bmiCategory').text(category).removeClass().addClass('badge rounded-pill fs-6 mt-2 pb-2 px-3 ' + badgeClass);
        $('#resultCard').removeClass('d-none');
        
        // Data bhejne se pehle Loading text dikhayega
        $('#serverMessage').text("Saving to database... ⏳").removeClass().addClass("text-warning");

        // Yahan se data Java Servlet ko ja raha hai
        $.ajax({
            url: 'BmiTrackerServlet', 
            type: 'POST',
            data: {
                weight: weight,
                height: heightCm,
                bmi: bmi,
                category: category
            },
            success: function(response) {
                // Jab Java "Success" bolega
                $('#serverMessage').text("Data saved to Database successfully! ✅").removeClass().addClass("text-success");
            },
            error: function(xhr, status, error) {
                // Agar server/database mein koi error aaya
                $('#serverMessage').text("Failed to save data! ❌").removeClass().addClass("text-danger");
                console.error("Backend Error:", error);
            }
        });
    });
});