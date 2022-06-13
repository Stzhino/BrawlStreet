package com.badlogic.mygame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.mygame.GameRunner;
import java.util.*;

public class DesktopLauncher {
	private static int merges;
	private static int sorts;
	private static ArrayList<Integer> arrayToSort;
	public static void main (String[] arg) {
		int[] arr = {1,2,3,4,5};
		System.out.println(traverseArr(arr)); // more than one base call + recursive
		iterTraverseArr(arr); // iterative ver
		ArrayList<Double> arrL = new ArrayList<>();
		arrL.add(12.3);
		arrL.add(234.52);
		arrL.add(24.3453);
		arrL.add(56.32);
		traverseArrL(arrL);
		iterTraverseArrL(arrL);
		binarySearch(arr, 0, 4,3);
		sort(arr, 0, 4);
		arrayToSort = new ArrayList<>();
		arrayToSort.add(2);
		arrayToSort.add(35);
		arrayToSort.add(345);
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("BrawlStreet");
		config.setWindowedMode(1000,600);
		config.useVsync(true);
		new Lwjgl3Application(new GameRunner(), config);
	}
	// recursive
	public static int traverseArr(int[] i)
	{
		if(i.length==0)
		{
			return 0;
		}
		int[] otherArr = new int[i.length-1];
		for(int j = 0; j<otherArr.length; j++)
		{
			otherArr[j]=i[j+1];
		}
		return i[0]*2+traverseArr(otherArr);
	}
	public static int iterTraverseArr(int[] i)
	{
		int sum = 0;
		for(int j = 0; j<i.length; j++)
		{
			sum+=(i[j]*2);
		}
		return sum;
	}

	public static void traverseArrL(ArrayList<Double> a)
	{
		if(a.size()>0)
		{
			System.out.println(a.remove(a.size()-1));
			traverseArrL(a);
		}
	}

	public static void iterTraverseArrL(ArrayList<Double> a)
	{
		for(int i = a.size()-1; i>=0; i--)
		{
			System.out.println(a.get(i));
		}
	}

	public static void merge(int arr[], int l, int m, int r)
	{
		merges++;
		//Determine sizes of two subarrays to be merged.
		int n1 = m - l + 1;
		int n2 = r - m;
		//Create temporary arrays.
		int left[] = new int[n1];
		int right[] = new int[n2];
		//Copy data to temporary arrays.
		for (int i = 0; i < n1; i++)
			left[i] = arr[l + i];
		for (int j = 0; j < n2; ++j)
			right[j] = arr[m + 1 + j];
		//Merge the temporary arrays in ascending order.
		int i = 0, j = 0, k = l;
		while (i < n1 && j < n2)
		{
			if (left[i] <= right[j])
			{
				arr[k] = left[i];
				i++;
			}
			else
			{
				arr[k] = right[j];
				j++;
			}
			k++;
		}
		//Copy remaining elements of the left array if any.
		while (i < n1)
		{
			arr[k] = left[i];
			i++;
			k++;
		}
		//Copy remaining elements of the right array if any.
		while (j < n2)
		{
			arr[k] = right[j];
			j++;
			k++;
		}
	}

	public static void sort(int arr[], int l, int r)
	{
		sorts++;
		if (l < r) {
			//Find the middle.
			int m =(l + r)/2;
			//Split the array recursively.
			sort(arr, l, m);
			sort(arr, m + 1, r);
			//Merge the sorted halves.
			merge(arr, l, m, r);
		}
	}
	public static int binarySearch(int[] arr, int left, int right, int x)
	{
		if (right >= left)
		{
			int mid = (left + right) / 2;
			if (arr[mid] == x)
			{
				return mid;
			}
			else if (arr[mid] > x)
			{
				return binarySearch(arr, left, mid - 1, x);
			}
			else
			{
				return binarySearch(arr, mid + 1, right, x);
			}
		}
		return -1;
	}
	public void mergeSortArrL(int indexStart, int indexEnd) {

		if (indexStart < indexEnd) {
			int middleElement = (indexEnd + indexStart) / 2;

			mergeSortArrL(indexStart, middleElement);
			mergeSortArrL(middleElement + 1, indexEnd);

			mergeArrayElements(indexStart, middleElement, indexEnd);
		}
	}

	public void mergeArrayElements(int indexStart, int indexMiddle, int indexEnd) {

		ArrayList<Integer> tempArray = new ArrayList<>();

		int getLeftIndex = indexStart;
		int getRightIndex = indexMiddle + 1;

		while (getLeftIndex <= indexMiddle && getRightIndex <= indexEnd) {

			if (arrayToSort.get(getLeftIndex) <= arrayToSort.get(getRightIndex)) {

				tempArray.add(arrayToSort.get(getLeftIndex));
				getLeftIndex++;

			} else {

				tempArray.add(arrayToSort.get(getRightIndex));
				getRightIndex++;

			}
		}

		while (getLeftIndex <= indexMiddle) {
			tempArray.add(arrayToSort.get(getLeftIndex));
			getLeftIndex++;
		}

		while (getRightIndex <= indexEnd) {
			tempArray.add(arrayToSort.get(getRightIndex));
			getRightIndex++;
		}


		for (int i = 0; i < tempArray.size(); indexStart++) {
			arrayToSort.set(indexStart, tempArray.get(i++));
		}

	}
}