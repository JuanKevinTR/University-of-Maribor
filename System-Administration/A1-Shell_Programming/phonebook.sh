#------------------------------
# @author J. Kevin Trujillo
# @version macOS Mojave 10.14.3 (18D109)
# web: juankevintrujillo.com
#------------------------------

#!/usr/bin/env bash

echo -e "\n----------------------------------------"
echo -e "\tWELCOME TO THE PHONEBOOK"
echo "----------------------------------------"

function existID(){
	local __ID=$1
	RETURN="false"

	((CHECK = 0))
	while read -r line; do
		if [ ! $CHECK -lt 3 ]; then
			((VALUE_ID_LINE = $(echo $line | cut -d'.' -f1)))
			if [ $VALUE_ID_LINE -eq $__ID ]; then	
				RETURN="true"
				break;
			fi
		fi
		((CHECK++))
	done < phonebook.dat
}

function checkID(){
	((COUNTER = 1))
	while read -r line; do
		if [ $COUNTER -eq 2 ]; then
			if [ "$1" == "ADD" ]; then
				((NUM_CONTACTS = $(echo $line | cut -d':' -f1) + 1 ))
				((ID = $NUM_CONTACTS ))

				existID $ID
				while [ $RETURN == "true" ]; do
					((ID++))
					existID $ID
				done
			elif [ "$1" == "DELETE" ]; then
				((NUM_CONTACTS = $(echo $line | cut -d':' -f1) - 1 ))
			else
				echo "==> Error updating ID"
				exit 1
			fi
			break;
		fi
		((COUNTER++))
	done < phonebook.dat
}

function add(){
	read -p $'\t'"Name: " NAME
	while [[ $NAME = *.* ]]; do
		echo -en "\t\tYou cannot use '.'\n"
		read -p $'\t'"Name: " NAME
	done

	read -p $'\t'"Surname: " SURNAME
	while [[ $SURNAME = *.* ]]; do
		echo -en "\t\tYou cannot use '.'\n"
		read -p $'\t'"Surname: " SURNAME
	done

	read -p $'\t'"Address: " ADDRESS
	while [[ $ADDRESS = *.* ]]; do
		echo -en "\t\tYou cannot use '.'\n"
		read -p $'\t'"Address: " ADDRESS
	done

	read -p $'\t'"Zip number: " ZIP
	while [[ $ZIP = *.* ]]; do
		echo -en "\t\tYou cannot use '.'\n"
		read -p $'\t'"Zip number: " ZIP
	done

	read -p $'\t'"City: " CITY
	while [[ $CITY = *.* ]]; do
		echo -en "\t\tYou cannot use '.'\n"
		read -p $'\t'"City: " CITY
	done

	read -p $'\t'"Phone number: " PHONE
	while [[ $PHONE = *.* ]]; do
		echo -en "\t\tYou cannot use '.'\n"
		read -p $'\t'"Phone number: " PHONE
	done

	checkID "ADD"

	echo "$ID.$NAME.$SURNAME.$ADDRESS.$ZIP.$CITY.$PHONE" >> phonebook.dat

	sed -i '' "2s/.*/$NUM_CONTACTS: CONTACTS/" phonebook.dat

	echo -e "==> CONTACT SAVED! \n"
}

function delete(){
	read -p $'\t'"Write the index to delete: " ID

	((FOUND = 0))
	existID $ID
	if [ $RETURN == "true" ]; then
		((FOUND++))
	fi

	if [ $FOUND -gt 0 ]; then
		echo -en "\n\t'$line'"
		read -p $'\n\t'"Delete this line? (y/n): " ANSWER
			if [ $ANSWER == y ]; then
				# WITH BACK-UP sed -ie "/$line/d" phonebook.dat
				echo "$(grep -v "$line" phonebook.dat)" > phonebook.dat

				checkID "DELETE"
				sed -i '' "2s/.*/$NUM_CONTACTS: CONTACTS/" phonebook.dat

				echo -e "==> CONTACT DELETED! \n"
			fi
	else
		echo "==> CONTACT NOT FOUND!"
	fi
}

function find(){
	read -p $'\t'"Write data to search: " SEARCH
	grep -i $SEARCH phonebook.dat
	#grep -iv $SEARCH phonebook.dat
}

function edit(){
	read -p $'\t'"Write the index to edit: " ID

	((FOUND = 0))
	existID $ID
	if [ $RETURN == "true" ]; then
		((FOUND++))
	fi

	if [ $FOUND -gt 0 ]; then
		echo -en "\n\t'$line' \n"

		#ID=$(echo $line | cut -d'.' -f1)	
		NAME=$(echo $line | cut -d'.' -f2)
		SURNAME=$(echo $line | cut -d'.' -f3)
		ADDRESS=$(echo $line | cut -d'.' -f4)
		ZIP=$(echo $line | cut -d'.' -f5)
		CITY=$(echo $line | cut -d'.' -f6)
		PHONE=$(echo $line | cut -d'.' -f7)

		echo -en "\nUse: --all --name --surname --address --zip --city --phone"
		read -p $'\t'"Option?: " OPTION

		case $OPTION in
			--name )
				NAME=""
				read -p $'\t'"Name: " NAME
				while [[ $NAME = *.* ]]; do
					echo -en "\t\tYou cannot use '.'\n"
					read -p $'\t'"Name: " NAME
				done
				;;
			--surname )
				SURNAME=""
				read -p $'\t'"Surname: " SURNAME
				while [[ $SURNAME = *.* ]]; do
					echo -en "\t\tYou cannot use '.'\n"
					read -p $'\t'"Surname: " SURNAME
				done
				;;
			--address )
				ADDRESS=""
				read -p $'\t'"Address: " ADDRESS
				while [[ $ADDRESS = *.* ]]; do
					echo -en "\t\tYou cannot use '.'\n"
					read -p $'\t'"Address: " ADDRESS
				done
				;;
			--zip)
				ZIP=""
				read -p $'\t'"Zip number: " ZIP
				while [[ $ZIP = *.* ]]; do
					echo -en "\t\tYou cannot use '.'\n"
					read -p $'\t'"Zip number: " ZIP
				done
				;;
			--city)
				CITY=""
				read -p $'\t'"City: " CITY
				while [[ $CITY = *.* ]]; do
					echo -en "\t\tYou cannot use '.'\n"
					read -p $'\t'"City: " CITY
				done
				;;
			--phone)
				PHONE=""
				read -p $'\t'"Phone number: " PHONE
				while [[ $PHONE = *.* ]]; do
					echo -en "\t\tYou cannot use '.'\n"
					read -p $'\t'"Phone number: " PHONE
				done
				;;
			--all)
				NAME=""
				read -p $'\t'"Name: " NAME
				while [[ $NAME = *.* ]]; do
					echo -en "\t\tYou cannot use '.'\n"
					read -p $'\t'"Name: " NAME
				done

				SURNAME=""
				read -p $'\t'"Surname: " SURNAME
				while [[ $SURNAME = *.* ]]; do
					echo -en "\t\tYou cannot use '.'\n"
					read -p $'\t'"Surname: " SURNAME
				done

				ADDRESS=""
				read -p $'\t'"Address: " ADDRESS
				while [[ $ADDRESS = *.* ]]; do
					echo -en "\t\tYou cannot use '.'\n"
					read -p $'\t'"Address: " ADDRESS
				done

				ZIP=""
				read -p $'\t'"Zip number: " ZIP
				while [[ $ZIP = *.* ]]; do
					echo -en "\t\tYou cannot use '.'\n"
					read -p $'\t'"Zip number: " ZIP
				done

				CITY=""
				read -p $'\t'"City: " CITY
				while [[ $CITY = *.* ]]; do
					echo -en "\t\tYou cannot use '.'\n"
					read -p $'\t'"City: " CITY
				done

				PHONE=""
				read -p $'\t'"Phone number: " PHONE
				while [[ $PHONE = *.* ]]; do
					echo -en "\t\tYou cannot use '.'\n"
					read -p $'\t'"Phone number: " PHONE
				done
				;;	
			* )
				echo -e "\n==> ERROR: Use only --name --surname --address --zip --city --phone --all"
		esac

		echo -en "\n\tOLD CONTACT DATA =>\t$line"
		NEW_LINE="$ID.$NAME.$SURNAME.$ADDRESS.$ZIP.$CITY.$PHONE"
		echo -en "\n\tNEW CONTACT DATA =>\t$NEW_LINE"

		read -p $'\n\t'"Edit this line? (y/n): " ANSWER
			if [ $ANSWER == y ]; then
				sed -i '' "s/$line/$NEW_LINE/g" phonebook.dat
				echo -e "==> CONTACT EDITED! \n"
			fi
	else
		echo "==> CONTACT NOT FOUND!"
	fi
}

########################################
#
#	MAIN
#
########################################

if [ $# -gt 0 ]; then
    
	if [ ! -f "phonebook.dat" ]; then
		echo -n > phonebook.dat
		((ID = 0))
		echo "########################################" >> phonebook.dat
		echo "$ID: CONTACTS" >> phonebook.dat
		echo "########################################" >> phonebook.dat
		echo "==> File 'phonebook.dat' created"
	fi

    case $1 in
		--add )
			echo -e "\nYOU ARE GONNA ADD A CONTACT!!\n"
				add
			echo -e "\n"
			;;
        --delete )
			echo -e "\nYOU ARE GONNA DELETE A CONTACT!!\n"
				delete
			echo -e "\n"
        	;;
        --find )
			echo -e "\nYOU ARE GONNA FIND A CONTACT!!\n"
				find
			echo -e "\n"
			;;
        --edit)
			echo -e "\nYOU ARE GONNA EDIT A CONTACT!!\n"
				edit
			echo -e "\n"
			;;
		--see)
			echo -e "\nYOU ARE WATCHING THE PHONEBOOK!!\n"
				cat phonebook.dat
			echo -e "\n"
			;;
        * )
			echo -e "\n==> ERROR: Use only --add --delete --find --edit OR --see"
			#exit 1
    esac
else
    echo -e "\n==> ERROR: Missing --add --delete --find --edit OR --see"
fi




