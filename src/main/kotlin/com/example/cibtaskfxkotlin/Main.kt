package com.example.cibtaskfxkotlin

import dto.Billionaire
import javafx.scene.chart.PieChart
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import services.billionaireServices
import tornadofx.*
import java.io.File
import java.io.FileWriter
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors


class Main() : View("Result") {
    val bR = billionaireServices()
    init {
        val file = File("src/main/resources/Billionaire.csv").bufferedReader()
        val csvParser = CSVParser(
            file, CSVFormat.DEFAULT.withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim()
        );

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
    }
    override val root = form {


        val industries = bR.groupByIndustries().keys
        val country = bR.sortingByCountry().keys
        val countryBillionaireList = ArrayList<List<Billionaire>>()

        val arr = ArrayList<PieChart.Data>().observable()

        for(i in 0..industries.size-1){
            arr.add(PieChart.Data(industries.elementAt(i), bR.groupByIndustries()[industries.elementAt(i)]?.size!!.toDouble()))
        }

        val dataCountry = ArrayList<PieChart.Data>().observable()

        for(i in 0..country.size-1){
            dataCountry.add(PieChart.Data(country.elementAt(i), bR.sortingByCountry()[country.elementAt(i)]?.size!!.toDouble()))
            countryBillionaireList.add(bR.sortingByCountry()[country.elementAt(i)]!!)
        }

        //piechart("Group by industries", arr)

        //piechart("Group by country", dataCountry)

        for(i in 0..3) {
            val billionaireList = bR.sortingByCountry()[country.elementAt(i)]?.toList()?.asObservable()
            tableview(billionaireList) {
                readonlyColumn("Name", Billionaire::name)
                readonlyColumn("NetWorth", Billionaire::netWorth)
                readonlyColumn("Country", Billionaire::country)
                readonlyColumn("Source", Billionaire::source)
                readonlyColumn("Rank", Billionaire::rank)
                readonlyColumn("Age", Billionaire::age)
                readonlyColumn("Industry", Billionaire::industry)
            }
        }


        groupCountry().openWindow()
        groupIndustries().openWindow()
    }
}
