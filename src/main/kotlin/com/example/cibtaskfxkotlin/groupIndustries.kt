package com.example.cibtaskfxkotlin

import dto.Billionaire
import javafx.scene.chart.PieChart
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import services.billionaireServices
import tornadofx.*
import java.io.File

class groupIndustries: View("Group by industries") {
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

        val industries = bR.groupByIndustries().keys

        val arr = ArrayList<PieChart.Data>().observable()

        for (i in 0..industries.size - 1) {
            arr.add(
                PieChart.Data(
                    industries.elementAt(i),
                    bR.groupByIndustries()[industries.elementAt(i)]?.size!!.toDouble()
                )
            )
        }


        piechart("Group by industries", arr)

    }
}
