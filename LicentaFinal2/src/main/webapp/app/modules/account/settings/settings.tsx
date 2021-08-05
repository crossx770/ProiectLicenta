import React, { useEffect  } from 'react';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { toast } from 'react-toastify';
import { Link, RouteComponentProps } from 'react-router-dom';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSession } from 'app/shared/reducers/authentication';
import { getJudets } from 'app/entities/judet/judet.reducer';
import { saveAccountSettings, reset } from './settings.reducer';
import { getCities } from 'app/entities/city/city.reducer';
import { values } from 'lodash';


export const SettingsPage = (props: RouteComponentProps<{id: string }>) => {


  const dispatch = useAppDispatch();
  const isNew = false;
  


  //const judetsDispatch = dispatch(getJudets())


  const judets =useAppSelector(state => state.judet.entities);
  const cities = useAppSelector (state => state.city.entities);
  const account = useAppSelector(state => state.authentication.account);
  const successMessage = useAppSelector(state => state.settings.successMessage);
  


  const handleClose = () => {
    props.history.push('/');
  };

  useEffect(() => {
    dispatch(getSession());
    dispatch(getCities());
    dispatch(getJudets());
    return () => {
      dispatch(reset());
    };
    
  }, []);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    }
  }, [successMessage]);

   const handleValidSubmit = values => {
    dispatch(
      saveAccountSettings({
        ...account,
        ...values,
      })
    );
    handleClose();
   };

  const defaultValues = () =>
  isNew
    ? {}
    : {
        ...account,
        cityId: account?.cityId,
      };  


    const validatePhoneNumber = (input_str) =>  {
          var re = /^(07)[(]?[0-9]{2}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{3}$/;
      
          return re.test(input_str);
      }

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="settings-title">User settings for {account.login}</h2>
          <ValidatedForm id="settings-form" onSubmit={handleValidSubmit} defaultValues={defaultValues()}>
            <ValidatedField
              name="firstName"
              label="First Name"
              id="firstName"
              placeholder="Your first name"
              validate={{
                required: { value: true, message: 'Your first name is required.' },
                minLength: { value: 1, message: 'Your first name is required to be at least 1 character' },
                maxLength: { value: 50, message: 'Your first name cannot be longer than 50 characters' },
              }}
              data-cy="firstname"
            />
            <ValidatedField
              name="lastName"
              label="Last Name"
              id="lastName"
              placeholder="Your last name"
              validate={{
                required: { value: true, message: 'Your last name is required.' },
                minLength: { value: 1, message: 'Your last name is required to be at least 1 character' },
                maxLength: { value: 50, message: 'Your last name cannot be longer than 50 characters' },
              }}
              data-cy="lastname"
            />
            <ValidatedField
              name="email"
              label="Email"
              placeholder={'Your email'}
              type="email"
              validate={{
                required: { value: true, message: 'Your email is required.' },
                minLength: { value: 5, message: 'Your email is required to be at least 5 characters.' },
                maxLength: { value: 254, message: 'Your email cannot be longer than 50 characters.' },
                validate: v => isEmail(v) || 'Your email is invalid.',
              }}
              data-cy="email"
            />

            <ValidatedField
              name="phone"
              label="Phone"
              placeholder={'Your phone number'}
              type="tel"
              validate={{
                required: { value: true, message: 'Your phone is required.' },
                minLength: { value: 10, message: 'Your phone is required to be at exactly 10 numbers.' },
                maxLength: { value: 10, message: 'Your phone is required to be at exactly 10 numbers.' },
                validate: v => validatePhoneNumber(v) || 'Your phone is invalid.',
              }}
              data-cy="email"
            />
           
            <ValidatedField id="settings-judet" name="judetId" data-cy="judet" label="Judet"  type="select" required>
            <option value="" key="0"/>
              {judets
                ? judets.map(otherEntity=> 
                    <option value={otherEntity.id}  key={otherEntity.id} >
                      {otherEntity.name}
                    </option>
                  )
                : null}
            </ValidatedField>

            <ValidatedField id="settings-city" name="cityId" data-cy="city" label="City"  type="select" required>
            <option value="" key="0"/>
              {cities
                ? cities.map(otherEntity=> 
                    <option value={otherEntity.id}  key={otherEntity.id} >
                      {otherEntity.name} - {otherEntity.judet.code}
                    </option>
                  )
                : null}
            </ValidatedField>

            <ValidatedField
              name="address"
              label="Address"
              placeholder={'Your address'}
              type="text"
              validate={{
                required: { value: true, message: 'Your address is required.' },
                minLength: { value: 10, message: 'Your address is required to be at least 10 characters.' },
                maxLength: { value: 85, message: 'Your address cannot be longer than 75 characters.' },
              }}
              data-cy="address"
            />
              
            <Button color="primary" type="submit" data-cy="submit" re>
              Save
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default SettingsPage;
