package services

import dto.Billionaire
import java.util.SortedMap

class billionaireServices {
    private val billionaireList = ArrayList<Billionaire>()

    fun getList() = billionaireList
    fun addBillionaire(billionaire: Billionaire) {
        billionaireList.add(billionaire)
    }

    fun sortingByCountry() = billionaireList.groupBy { it.country }

    fun groupByIndustries() = billionaireList.groupBy { it.industry }
    fun countryWithManyBillionaire(): SortedMap<Int, String> {
        var sortByCountry = sortingByCountry()
        val bestBillionaire = HashMap<Int, String>();

        for (country in sortByCountry.keys) {
            if (country != null) {
                bestBillionaire[sortByCountry[country]?.size!!] = country
            }
        }
        return bestBillionaire.toSortedMap()
    }

    fun industriesWithManyBillionaire(): SortedMap<Int, String> {
        val industries = billionaireList.groupBy { it.industry }
        val bestBillionaire = HashMap<Int, String>();

        for(i in industries.keys) {
            if(i != null){
                bestBillionaire[industries[i]?.size!!] = i
            }
        }
        return bestBillionaire.toSortedMap()
    }

    fun printGroupByCountry() {
        var sortByCountry = sortingByCountry()
        for (country in sortByCountry.keys) {
            println("===${country}===")
            for (i in sortByCountry[country]!!) {
                println(i)
            }
        }
    }

    fun printBestBillionaire(count: Int) {
        for (i in countryWithManyBillionaire()) {
            if (i.key > count) {
                println(i)
            }
        }
    }

    fun printGroupByIndustries(){
        var sortByIndustries = groupByIndustries()
        for (industries in sortByIndustries.keys) {
            println("===${industries}===")
            for (i in sortByIndustries[industries]!!) {
                println(i)
            }
        }
    }
    fun printCountBillionaireByIndustries(count: Int) {
        for (i in industriesWithManyBillionaire()) {
            if (i.key > count) {
                println(i)
            }
        }
    }
}
