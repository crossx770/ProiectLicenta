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
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { ISubCategory } from 'app/shared/model/sub-category.model';
import { getEntities as getSubCategories } from 'app/entities/sub-category/sub-category.reducer';
import { getEntity, updateEntity, createEntity, reset } from './post.reducer';
import { IPost } from 'app/shared/model/post.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { height } from '@fortawesome/free-solid-svg-icons/faCogs';
import { now } from 'lodash';
import moment from "moment";

export const PostUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const judets = useAppSelector(state => state.judet.entities);
  const cities = useAppSelector(state => state.city.entities);
  const categories = useAppSelector(state => state.category.entities);
  const subCategories = useAppSelector(state => state.subCategory.entities);
  const postEntity = useAppSelector(state => state.post.entity);
  const loading = useAppSelector(state => state.post.loading);
  const updating = useAppSelector(state => state.post.updating);
  const updateSuccess = useAppSelector(state => state.post.updateSuccess);
  const user = useAppSelector(state => state.authentication.account);

  const handleClose = () => {
    props.history.push('/post');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
    dispatch(getAccount());
    dispatch(getJudets({}));
    dispatch(getCities({}));
    dispatch(getCategories({}));
    dispatch(getSubCategories({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.created_at = convertDateTimeToServer(values.created_at);

    const entity = {
      ...postEntity,
      ...values,
      user_post: user, 
      judet_post: judets.find(it => it.id.toString() === values.judet_postId.toString()),
      city_post: cities.find(it => it.id.toString() === values.city_postId.toString()),
      category_post: categories.find(it => it.id.toString() === values.category_postId.toString()),
      subCategory_post: subCategories.find(it => it.id.toString() === values.subCategory_postId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          created_at: displayDefaultDateTime(),
        }
      : {
          ...postEntity,
          created_at: convertDateTimeFromServer(moment().format("YYYY-MM-DD HH:mm:ss")),
          judet_postId: postEntity?.judet_post?.id,
          city_postId: postEntity?.city_post?.id,
          category_postId: postEntity?.category_post?.id,
          subCategory_postId: postEntity?.subCategory_post?.id,
        };

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
              <ValidatedField id="post-judet_post" name="judet_postId" data-cy="judet_post" label="Judet Post" type="select" required>
                <option value="" key="0" />
                {judets
                  ? judets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="post-city_post" name="city_postId" data-cy="city_post" label="City Post" type="select" required>
                <option value="" key="0" />
                {cities
                  ? cities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="post-category_post"
                name="category_postId"
                data-cy="category_post"
                label="Category Post"
                type="select"
                required
              >
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="post-subCategory_post"
                name="subCategory_postId"
                data-cy="subCategory_post"
                label="Sub Category Post"
                type="select"
                required
              >
                <option value="" key="0" />
                {subCategories
                  ? subCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/post" replace color="info">
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
