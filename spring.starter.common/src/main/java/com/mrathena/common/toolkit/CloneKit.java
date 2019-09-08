package com.mrathena.common.toolkit;

import com.mrathena.common.exception.ServiceException;

import java.io.*;

/**
 * @author mrathena on 2019/5/27 11:16
 */
public final class CloneKit {

	public static void main(String[] args) {
		String mrathena = "mrathena";
		String clone = CloneKit.clone(mrathena);
		System.out.println(clone);
	}

	private CloneKit() {}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T clone(T object) {
		T newObject;
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		     ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
			objectOutputStream.writeObject(object);
			try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			     ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
				newObject = (T) objectInputStream.readObject();
			}
			return newObject;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
