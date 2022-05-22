package com.example.cibtaskfxkotlin

import dto.Billionaire
import javafx.scene.Parent
import javafx.scene.chart.PieChart
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import services.billionaireServices
import tornadofx.View
import tornadofx.form
import tornadofx.observable
import tornadofx.piechart
import java.io.File

class groupCountry: View("Group by country") {
    override val root = form {
        val file = File("src/main/resources/Billionaire.csv").bufferedReader()
        val csvParser = CSVParser(
            file, CSVFormat.DEFAULT.withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim()
        );


        val bR = billionaireServices()
        for (csvRecord in csvParser) {
            bR.addBillionaire(
                Billionaire(
                    name = csvRecord[0],
                    netWorth = csvRecord[1],
                    country = csvRecord[2],
                    source = csvRecord[3],
                    rank = csvRecord[4].toInt(),
                    age = csvRecord[5],
                    industry = csvRecord[6]
                )
            )
        }

        val country = bR.sortingByCountry().keys


        val dataCountry = ArrayList<PieChart.Data>().observable()

        for(i in 0..country.size-1){
            dataCountry.add(PieChart.Data(country.elementAt(i), bR.sortingByCountry()[country.elementAt(i)]?.size!!.toDouble()))
        }


        piechart("Group by country", dataCountry)

    }
}
