package com.github.linkyness

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.BufferedReader
import java.io.FileReader
import java.io.FileOutputStream
import java.util.*

fun main() {
    val fileReader: BufferedReader?
    val csvParser: CSVParser?

    fileReader = BufferedReader(FileReader("src/main/resources/customer.csv"))
    csvParser = CSVParser(
        fileReader,
        CSVFormat.newFormat(';').withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
    )

    val customers = ArrayList<Customer>()
    val csvRecords = csvParser.records

    for (csvRecord in csvRecords) {
        val customer = Customer(
            csvRecord.get("id"),
            csvRecord.get("name"),
            csvRecord.get("address"),
            Integer.parseInt(csvRecord.get("age"))
        )

        customers.add(customer)
    }
    fileReader.close()
    csvParser.close()

    customers.forEach{ customer ->
        println(customer)
    }

    val columns = arrayOf<String>("id", "name", "address", "age")

    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("MiHoja")

    val headerRow = sheet.createRow(0)
    columns.forEachIndexed { index, column ->
        headerRow.createCell(index).setCellValue(column)
    }


    customers.forEachIndexed{ index, customer ->
        val row = sheet.createRow(index + 1)
        row.createCell(0).setCellValue(customer.id)
        row.createCell(1).setCellValue(customer.name)
        row.createCell(2).setCellValue(customer.address)
        row.createCell(3).setCellValue(customer.age.toString())
    }

    val output = FileOutputStream("src/main/resources/customer.xlsx")
    workbook.write(output)
    workbook.close()
}