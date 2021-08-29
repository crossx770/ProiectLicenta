import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUser } from 'app/shared/model/user.model';
import { getUser, getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getAccount } from 'app/shared/reducers/authentication';
import { IJudet } from 'app/shared/model/judet.model';
import { getEntities as getJudets } from 'app/entities/judet/judet.reducer';
import { ICity } from 'app/shared/model/city.model';
import { getEntities as getCities } from 'app/entities/city/city.reducer';
import { ICategory } from 'app/shared/model/category.model';
import { ISubCategory } from 'app/shared/model/sub-category.model';
import { getEntity, updateEntity, createEntity, reset } from './post.reducer';
import { IPost } from 'app/shared/model/post.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { height } from '@fortawesome/free-solid-svg-icons/faCogs';
import { now } from 'lodash';
import {judet,category, AUTHORITIES } from 'app/config/constants';
import moment from "moment";
import { AccountMenu } from 'app/shared/layout/menus';

export const PostUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const postEntity = useAppSelector(state => state.post.entity);
  const loading = useAppSelector(state => state.post.loading);
  const updating = useAppSelector(state => state.post.updating);
  const updateSuccess = useAppSelector(state => state.post.updateSuccess);
  const user = useAppSelector(state => state.authentication.account);

  var judetInitial = useAppSelector(state => state.post.entity.judet);
  var categoryInitial = useAppSelector(state => state.post.entity.category);
  const prev = props.location.state;
  const [judetTemp,setJudetTemp] = useState('');
  const [categoryTemp,setCategoryTemp] = useState('');

  const handleClose = () => {
    if(prev) {
      props.history.push(prev["prevPath"]);
    }
    else {
      props.history.push('/');
    }
  };

  const back =() => {
    if(prev) {
      props.history.push(prev["prevPath"]);
    }
    else {
      props.history.push('/');
    }
  }

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
    dispatch(getAccount());
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    
    const entity = {
      ...postEntity,
      ...values,
      user_post: user
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    !isNew
      ? {
          ...postEntity,
        }
        : stop;

  const handleChangeJudet = (e) => {
    setJudetTemp( e.target.value);
  }

  const handleChangeCategory = (e) => {
    setCategoryTemp(e.target.value);
  }


  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="licentaApp.post.home.createOrEditLabel" data-cy="PostCreateUpdateHeading">
            Create or edit a Post
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              <ValidatedField
                label="Title"
                id="post-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 60, message: 'This field cannot be longer than 60 characters.' },
                }}
              />
              <ValidatedField
                label="Description"
                id="post-description"
                name="description"
                data-cy="description"
                type="textarea"
                style= {{height: "calc(10em + 1.5rem + 2px)"}}
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  maxLength: { value: 300, message: 'This field cannot be longer than 300 characters.' },
                }}
              />
              <ValidatedField
                label="Price"
                id="post-price"
                name="price"
                data-cy="price"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField id="post-judet" name="judet" data-cy="judet" label="Judet Post" type="select" required onChange={handleChangeJudet}>
                <option key="0"> </option>
                {judet.map((judet1, index) => {
              return <option key={judet1.id}>{judet1.name}</option>
            })}
              </ValidatedField>
              <ValidatedField id="post-city" name="city" data-cy="city" label="City Post" type="select" required>
                <option key="0"> </option>
                {judet.map((judet1, index) => {
                if(judetTemp == '') {
                  if(judet1.name == judetInitial){
                      return judet1.city.map((city, index2) => {
                          return <option key={city.id}>{city.name}</option>
                      })
                    }
                } else {
                  if(judet1.name == judetTemp){
                    return judet1.city.map((city, index2) => {
                        return <option key={city.id}>{city.name}</option>
                    })
                  }
                }
                })}
              </ValidatedField>
              <ValidatedField
                id="post-category"
                name="category"
                data-cy="category"
                label="Category Post"
                type="select"
                onChange={handleChangeCategory}
                required
              >
                <option key="0"> </option>
                {category.map((cat, index) => {
              return <option key={cat.id}>{cat.name}</option>
            })}
              </ValidatedField>
              <ValidatedField
                id="post-subcategory"
                name="subcategory"
                data-cy="subcategory"
                label="Subcategory Post"
                type="select"
                required
              >
                <option key="0"> </option>
                {category.map((cat, index) => {
                if(categoryTemp == '') {
                  if(cat.name == categoryInitial){
                      return cat.subcategory.map((subcat, index2) => {
                          return <option key={subcat.id}>{subcat.name}</option>
                      })
                    }
                } else {
                  if(cat.name == categoryTemp){
                    return cat.subcategory.map((subcat, index2) => {
                        return <option key={subcat.id}>{subcat.name}</option>
                    })
                  }
                }
                })}
              </ValidatedField>
              <Button id="cancel-save" data-cy="entityCreateCancelButton" onClick={back} replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PostUpdate;
