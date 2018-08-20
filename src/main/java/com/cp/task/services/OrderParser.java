package com.cp.task.services;

import com.cp.task.enums.OrderFieldsEnum;

import java.util.EnumMap;
import java.util.List;

/**
 * Contains base methods to parse files to transitional format
 * Each implementation must parse its own format
 *
 * @author Ilya Savchenko
 * @email ilyasavchenko1990@gmail.com
 * @date 07.08.2018
 */
public interface OrderParser {

    /**
     * Parse file to transitional format
     *
     * @param fileName Input file name with extension separated by dor
     * @return Transitional format for converting into view
     */
    List<EnumMap<OrderFieldsEnum, Object>> parse(String fileName);

    /**
     * @param fileName File name
     * @return Returns true if implementation able to parse given file
     */
    boolean checkFileExtension(String fileName);
}
